'''
Created on 08 abr. 2019
@author: jfluna
Examples of the Deep learnin with python book by Jason Brownlee Part IV.
'''
from keras.models import Sequential
from keras.layers import Dense
import numpy
import os

# MLP for Pima Indians Dataset serialize to JSON and HDF5
def ejemplo01():
    from keras.models import model_from_json
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load pima indians dataset
    dataset = numpy.loadtxt("../datasets/pima-indians-diabetes.data.csv", delimiter=",")
    # split into input (X) and output (Y) variables
    X = dataset[:,0:8]
    Y = dataset[:,8]
    # create model
    model = Sequential()
    model.add(Dense(12, input_dim=8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(1, kernel_initializer='uniform', activation='sigmoid'))
    # Compile model
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    # Fit the model
    model.fit(X, Y, epochs=150, batch_size=10, verbose=0)
    # evaluate the model
    scores = model.evaluate(X, Y, verbose=0)
    print("%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))
    # serialize model to JSON
    model_json = model.to_json()
    with open("../models/model.json", "w") as json_file:
        json_file.write(model_json)
    # serialize weights to HDF5
    model.save_weights("../models/model.h5")
    print("Saved model to disk")
    json_file = open('../models/model.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = model_from_json(loaded_model_json)
    # load weights into new model
    loaded_model.load_weights("../models/model.h5")
    print("Loaded model from disk")
    # evaluate loaded model on test data
    loaded_model.compile(loss='binary_crossentropy', optimizer='rmsprop', metrics=['accuracy'])
    score = loaded_model.evaluate(X, Y, verbose=0)
    print ("%s: %.2f%%" % (loaded_model.metrics_names[1], score[1]*100))
    
# MLP for Pima Indians Dataset serialize to YAML and HDF5
def ejemplo02():
    from keras.models import model_from_yaml
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load pima indians dataset
    dataset = numpy.loadtxt("../datasets/pima-indians-diabetes.data.csv", delimiter=",")
    # split into input (X) and output (Y) variables
    X = dataset[:,0:8]
    Y = dataset[:,8]
    # create model
    model = Sequential()
    model.add(Dense(12, input_dim=8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(1, kernel_initializer='uniform', activation='sigmoid'))
    # Compile model
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    # Fit the model
    model.fit(X, Y, epochs=150, batch_size=10, verbose=0)
    # evaluate the model
    scores = model.evaluate(X, Y, verbose=0)
    print("%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))
    # serialize model to YAML
    model_yaml = model.to_yaml()
    with open("../models/model2.yaml", "w") as yaml_file:
        yaml_file.write(model_yaml)
    # serialize weights to HDF5
    model.save_weights("../models/model2.h5")
    print("Saved model to disk")
    # load YAML and create model
    yaml_file = open('../models/model2.yaml', 'r')
    loaded_model_yaml = yaml_file.read()
    yaml_file.close()
    loaded_model = model_from_yaml(loaded_model_yaml)
    # load weights into new model
    loaded_model.load_weights("../models/model2.h5")
    print("Loaded model from disk")
    # evaluate loaded model on test data
    loaded_model.compile(loss='binary_crossentropy', optimizer='rmsprop', metrics=['accuracy'])
    score = loaded_model.evaluate(X, Y, verbose=0)
    print ("%s: %.2f%%" % (loaded_model.metrics_names[1], score[1]*100))

# Checkpoint the weights when validation accuracy improves
def ejemplo03():
    from keras.callbacks import ModelCheckpoint
    import matplotlib.pyplot as plt
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load pima indians dataset
    dataset = numpy.loadtxt("../datasets/pima-indians-diabetes.data.csv", delimiter=",")
    # split into input (X) and output (Y) variables
    X = dataset[:,0:8]
    Y = dataset[:,8]
    # create model
    model = Sequential()
    model.add(Dense(12, input_dim=8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(1, kernel_initializer='uniform', activation='sigmoid'))
    # Compile model
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    # checkpoint
    filepath="../models/weights-improvement-{epoch:02d}-{val_acc:.2f}.hdf5"
    checkpoint = ModelCheckpoint(filepath, monitor='val_acc', verbose=1, save_best_only=True, mode='max')
    callbacks_list = [checkpoint]
    # Fit the model
    model.fit(X, Y, validation_split=0.33, epochs=150, batch_size=10,
    callbacks=callbacks_list, verbose=0)

# Checkpoint the weights for best model on validation accuracy
def ejemplo04():
    from keras.callbacks import ModelCheckpoint
    import matplotlib.pyplot as plt
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load pima indians dataset
    dataset = numpy.loadtxt("../datasets/pima-indians-diabetes.data.csv", delimiter=",")
    # split into input (X) and output (Y) variables
    X = dataset[:,0:8]
    Y = dataset[:,8]
    # create model
    model = Sequential()
    model.add(Dense(12, input_dim=8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(1, kernel_initializer='uniform', activation='sigmoid'))
    # Compile model
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    # checkpoint
    filepath="../models/weights.best.hdf5"
    checkpoint = ModelCheckpoint(filepath, monitor='val_acc', verbose=1, save_best_only=True, mode='max')
    callbacks_list = [checkpoint]
    # Fit the model
    model.fit(X, Y, validation_split=0.33, epochs=150, batch_size=10,
    callbacks=callbacks_list, verbose=0)

# How to load and use weights from a checkpoint    
def ejemplo05():
    from keras.callbacks import ModelCheckpoint
    import matplotlib.pyplot as plt
    import matplotlib.pyplot as plt
    import numpy
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # create model
    model = Sequential()
    model.add(Dense(12, input_dim=8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(1, kernel_initializer='uniform', activation='sigmoid'))
    # load weights
    model.load_weights("../models/weights.best.hdf5")
    # Compile model (required to make predictions)
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    print("Created model and loaded weights from file")
    # load pima indians dataset
    dataset = numpy.loadtxt("../datasets/pima-indians-diabetes.data.csv", delimiter=",")
    # split into input (X) and output (Y) variables
    X = dataset[:,0:8]
    Y = dataset[:,8]
    # estimate accuracy on whole dataset using loaded weights
    scores = model.evaluate(X, Y, verbose=0)
    print("%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))
    

# Visualize training history
def ejemplo06():  
    import matplotlib.pyplot as plt
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load pima indians dataset
    dataset = numpy.loadtxt("../datasets/pima-indians-diabetes.data.csv", delimiter=",")
    # split into input (X) and output (Y) variables
    X = dataset[:,0:8]
    Y = dataset[:,8]
    # create model
    model = Sequential()
    model.add(Dense(12, input_dim=8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(8, kernel_initializer='uniform', activation='relu'))
    model.add(Dense(1, kernel_initializer='uniform', activation='sigmoid'))
    # Compile model
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    # Fit the model
    history = model.fit(X, Y, validation_split=0.33, epochs=150, batch_size=10, verbose=0)
    # list all data in history
    print(history.history.keys())
    # summarize history for accuracy
    plt.plot(history.history['acc'])
    plt.plot(history.history['val_acc'])
    plt.title('model accuracy')
    plt.ylabel('accuracy')
    plt.xlabel('epoch')
    plt.legend(['train', 'test'], loc='upper left')
    plt.show()
    # summarize history for loss
    plt.plot(history.history['loss'])
    plt.plot(history.history['val_loss'])
    plt.title('model loss')
    plt.ylabel('loss')
    plt.xlabel('epoch')
    plt.legend(['train', 'test'], loc='upper left')
    plt.show()


# Using Dropout on the Visible Layer
def ejemplo07(): 
    import pandas
    from keras.wrappers.scikit_learn import KerasClassifier
    from sklearn.model_selection import StratifiedKFold
    from sklearn.model_selection import cross_val_score
    from sklearn.preprocessing import LabelEncoder
    from sklearn.preprocessing import StandardScaler
    from sklearn.pipeline import Pipeline
    from sklearn.model_selection import GridSearchCV
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load dataset
    dataframe = pandas.read_csv("../datasets/sonar.all-data.csv", header=None)
    dataset = dataframe.values
    # split into input (X) and output (Y) variables
    X = dataset[:,0:60].astype(float)
    Y = dataset[:,60]
    # encode class values as integers
    encoder = LabelEncoder()
    encoder.fit(Y)
    encoded_Y = encoder.transform(Y)
    numpy.random.seed(seed)
    estimators = []
    estimators.append(('standardize', StandardScaler()))
    estimators.append(('mlp', KerasClassifier(build_fn=create_baseline, epochs=300, batch_size=16, verbose=0)))
    pipeline = Pipeline(estimators)
    kfold = StratifiedKFold(n_splits=10, shuffle=True, random_state=seed)
    results = cross_val_score(pipeline, X, encoded_Y, cv=kfold)
    print("without dropout accuracy: %.2f%% (%.2f%%)" % (results.mean()*100, results.std()*100))
    
    # dropout in the input layer with weight constraint
    numpy.random.seed(seed)
    estimators = []
    estimators.append(('standardize', StandardScaler()))
    estimators.append(('mlp', KerasClassifier(build_fn=create_model1, epochs=300, batch_size=16, verbose=0)))
    pipeline = Pipeline(estimators)
    kfold = StratifiedKFold(n_splits=10, shuffle=True, random_state=seed)
    results = cross_val_score(pipeline, X, encoded_Y, cv=kfold)
    print("With dropout accuracy: %.2f%% (%.2f%%)" % (results.mean()*100, results.std()*100))


def create_baseline():
    from keras.optimizers import SGD
    # create model
    model = Sequential()
    model.add(Dense(60, input_dim=60, kernel_initializer='normal', activation='relu'))
    model.add(Dense(30, kernel_initializer='normal', activation='relu'))
    model.add(Dense(1, kernel_initializer='normal', activation='sigmoid'))
    # Compile model
    sgd = SGD(lr=0.01, momentum=0.8, decay=0.0, nesterov=False)
    model.compile(loss='binary_crossentropy', optimizer=sgd, metrics=['accuracy'])
    return model

# dropout in the input layer with weight constraint
def create_model1():
    from keras.optimizers import SGD
    from keras.layers import Dropout
    from keras.constraints import maxnorm
    # create model
    model = Sequential()
    model.add(Dropout(0.2, input_shape=(60,)))
    model.add(Dense(60, kernel_initializer='normal', activation='relu', kernel_constraint=maxnorm(3)))
    model.add(Dense(30, kernel_initializer='normal', activation='relu', kernel_constraint=maxnorm(3)))
    model.add(Dense(1, kernel_initializer='normal', activation='sigmoid'))
    # Compile model
    sgd = SGD(lr=0.1, momentum=0.9, decay=0.0, nesterov=False)
    model.compile(loss='binary_crossentropy', optimizer=sgd, metrics=['accuracy'])
    return model
   
# dropout in hidden layers with weight constraint
def ejemplo08(): 
    import pandas
    from keras.wrappers.scikit_learn import KerasClassifier
    from sklearn.model_selection import StratifiedKFold
    from sklearn.model_selection import cross_val_score
    from sklearn.preprocessing import LabelEncoder
    from sklearn.preprocessing import StandardScaler
    from sklearn.pipeline import Pipeline
    from sklearn.model_selection import GridSearchCV
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load dataset
    dataframe = pandas.read_csv("../datasets/sonar.all-data.csv", header=None)
    dataset = dataframe.values
    # split into input (X) and output (Y) variables
    X = dataset[:,0:60].astype(float)
    Y = dataset[:,60]
    # encode class values as integers
    encoder = LabelEncoder()
    encoder.fit(Y)
    encoded_Y = encoder.transform(Y)
    numpy.random.seed(seed)
    estimators = []
    estimators.append(('standardize', StandardScaler()))
    estimators.append(('mlp', KerasClassifier(build_fn=create_baseline, epochs=300, batch_size=16, verbose=0)))
    pipeline = Pipeline(estimators)
    kfold = StratifiedKFold(n_splits=10, shuffle=True, random_state=seed)
    results = cross_val_score(pipeline, X, encoded_Y, cv=kfold)
    print("without dropout accuracy: %.2f%% (%.2f%%)" % (results.mean()*100, results.std()*100))
    
    # dropout in hidden layers with weight constraint
    numpy.random.seed(seed)
    estimators = []
    estimators.append(('standardize', StandardScaler()))
    estimators.append(('mlp', KerasClassifier(build_fn=create_model2, epochs=300, batch_size=16, verbose=0)))
    pipeline = Pipeline(estimators)
    kfold = StratifiedKFold(n_splits=10, shuffle=True, random_state=seed)
    results = cross_val_score(pipeline, X, encoded_Y, cv=kfold)
    print("With dropout accuracy: %.2f%% (%.2f%%)" % (results.mean()*100, results.std()*100))
    
# dropout in hidden layers with weight constraint
def create_model2():
    from keras.optimizers import SGD
    from keras.layers import Dropout
    from keras.constraints import maxnorm
    # create model
    model = Sequential()
    model.add(Dense(60, input_dim=60, kernel_initializer='normal', activation='relu', kernel_constraint=maxnorm(3)))
    model.add(Dropout(0.2))
    model.add(Dense(30, kernel_initializer='normal', activation='relu', kernel_constraint=maxnorm(3)))
    model.add(Dropout(0.2))
    model.add(Dense(1, kernel_initializer='normal', activation='sigmoid'))
    # Compile model
    sgd = SGD(lr=0.1, momentum=0.9, decay=0.0, nesterov=False)
    model.compile(loss='binary_crossentropy', optimizer=sgd, metrics=['accuracy'])
    return model    
    

#Time-Based Learning Rate Schedule    
def ejemplo09():
    import pandas
    from keras.optimizers import SGD
    from sklearn.preprocessing import LabelEncoder
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load dataset
    dataframe = pandas.read_csv("../datasets/ionosphere.data.csv", header=None)
    dataset = dataframe.values
    # split into input (X) and output (Y) variables
    X = dataset[:,0:34].astype(float)
    Y = dataset[:,34]
    # encode class values as integers
    encoder = LabelEncoder()
    encoder.fit(Y)
    Y = encoder.transform(Y)
    # create model
    model = Sequential()
    model.add(Dense(34, input_dim=34, kernel_initializer='normal', activation='relu'))
    model.add(Dense(1, kernel_initializer='normal', activation='sigmoid'))
    # Compile model
    repochs = 50
    learning_rate = 0.1
    decay_rate = learning_rate / repochs
    momentum = 0.8
    sgd = SGD(lr=learning_rate, momentum=momentum, decay=decay_rate, nesterov=False)
    model.compile(loss='binary_crossentropy', optimizer=sgd, metrics=['accuracy'])
    # Fit the model
    model.fit(X, Y, validation_split=0.33, epochs=repochs, batch_size=28)
    
# Drop-Based Learning Rate Schedule
def ejemplo10():
    from keras.callbacks import LearningRateScheduler
    import math
    import pandas
    from keras.optimizers import SGD
    from sklearn.preprocessing import LabelEncoder
    
    # learning rate schedule
    def step_decay(epoch):
        initial_lrate = 0.1
        drop = 0.5
        epochs_drop = 10.0
        lrate = initial_lrate * math.pow(drop, math.floor((1+epoch)/epochs_drop))
        return lrate
    
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load dataset
    dataframe = pandas.read_csv("../datasets/ionosphere.data.csv", header=None)
    dataset = dataframe.values
    # split into input (X) and output (Y) variables
    X = dataset[:,0:34].astype(float)
    Y = dataset[:,34]
    # encode class values as integers
    encoder = LabelEncoder()
    encoder.fit(Y)
    Y = encoder.transform(Y)
    # create model
    model = Sequential()
    model.add(Dense(34, input_dim=34, kernel_initializer='normal', activation='relu'))
    model.add(Dense(1, kernel_initializer='normal', activation='sigmoid'))
    # Compile model
    sgd = SGD(lr=0.0, momentum=0.9, decay=0.0, nesterov=False)
    model.compile(loss='binary_crossentropy', optimizer=sgd, metrics=['accuracy'])
    # learning schedule callback
    lrate = LearningRateScheduler(step_decay)
    callbacks_list = [lrate]
    # Fit the model
    model.fit(X, Y, validation_split=0.33, epochs=50, batch_size=28, callbacks=callbacks_list)
    
    
    
    
    
    
    
    
    