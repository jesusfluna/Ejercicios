'''
Created on 08 abr. 2019
@author: jfluna
Examples of the Deep learnin with python book by Jason Brownlee Part V.
'''
from keras.models import Sequential
from keras.layers import Dense
from keras.datasets import mnist
from keras.layers import Dropout
from keras.utils import np_utils
import numpy

#Baseline Model with Multi-Layer Perceptrons with MNIST
def ejemplo01():
    import matplotlib.pyplot as plt
    seed = 7
    numpy.random.seed(seed)
    # load (downloaded if needed) the MNIST dataset
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # flatten 28*28 images to a 784 vector for each image
    num_pixels = X_train.shape[1] * X_train.shape[2]
    X_train = X_train.reshape(X_train.shape[0], num_pixels).astype('float32')
    X_test = X_test.reshape(X_test.shape[0], num_pixels).astype('float32')
    # normalize inputs from 0-255 to 0-1
    X_train = X_train / 255
    X_test = X_test / 255
    # one hot encode outputs
    y_train = np_utils.to_categorical(y_train)
    y_test = np_utils.to_categorical(y_test)
    num_classes = y_test.shape[1]
    # build the model
    model = baseline_model(num_pixels,num_classes)
    # Fit the model
    model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=10, batch_size=200, verbose=2)
    # Final evaluation of the model
    scores = model.evaluate(X_test, y_test, verbose=0)
    print("Baseline Error: %.2f%%" % (100-scores[1]*100))


def baseline_model(num_pixels,num_classes):
    # create model
    model = Sequential()
    model.add(Dense(num_pixels, input_dim=num_pixels, kernel_initializer='normal', activation='relu'))
    model.add(Dense(num_classes, kernel_initializer='normal', activation='softmax'))
    # Compile model
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    return model   
    
# Simple Convolutional Neural Network for MNIST
def ejemplo02():
    import numpy
    from keras.datasets import mnist
    from keras.utils import np_utils
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load (downloaded if needed) the MNIST dataset
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # reshape to be [samples][channels][width][height]
    X_train = X_train.reshape(X_train.shape[0], 28, 28, 1).astype('float32')
    X_test = X_test.reshape(X_test.shape[0], 28, 28, 1).astype('float32')
    # normalize inputs from 0-255 to 0-1
    X_train = X_train / 255
    X_test = X_test / 255
    # one hot encode outputs
    y_train = np_utils.to_categorical(y_train)
    y_test = np_utils.to_categorical(y_test)
    num_classes = y_test.shape[1] 
    
    # build the model
    model = baseline_model_conv(num_classes)
    # Fit the model
    model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=10, batch_size=200, verbose=2)
    # Final evaluation of the model
    scores = model.evaluate(X_test, y_test, verbose=0)
    print("Baseline Error: %.2f%%" % (100-scores[1]*100))
    
def baseline_model_conv(num_classes):
    from keras.models import Sequential
    from keras.layers.convolutional import Conv2D
    from keras.layers.convolutional import MaxPooling2D
    from keras.layers import Dense
    from keras.layers import Dropout
    from keras.layers import Flatten
    # create model
    model = Sequential()
    model.add(Conv2D(32, (5, 5), padding='valid', input_shape=(28, 28, 1), activation='relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Dropout(0.2))
    model.add(Flatten())
    model.add(Dense(128, activation='relu'))
    model.add(Dense(num_classes, activation='softmax'))
    # Compile model
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    return model

# Simple Convolutional Neural Network for MNIST - LARGER
def ejemplo03():
    import numpy
    from keras.datasets import mnist
    from keras.utils import np_utils
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load (downloaded if needed) the MNIST dataset
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # reshape to be [samples][channels][width][height]
    X_train = X_train.reshape(X_train.shape[0], 28, 28, 1).astype('float32')
    X_test = X_test.reshape(X_test.shape[0], 28, 28, 1).astype('float32')
    # normalize inputs from 0-255 to 0-1
    X_train = X_train / 255
    X_test = X_test / 255
    # one hot encode outputs
    y_train = np_utils.to_categorical(y_train)
    y_test = np_utils.to_categorical(y_test)
    num_classes = y_test.shape[1] 
    # build the model
    model = larger_model(num_classes)
    # Fit the model
    model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=10, batch_size=200, verbose=2)
    # Final evaluation of the model
    scores = model.evaluate(X_test, y_test, verbose=0)
    print("Baseline Error: %.2f%%" % (100-scores[1]*100))
    
def larger_model(num_classes):
    from keras.models import Sequential
    from keras.layers.convolutional import Conv2D
    from keras.layers.convolutional import MaxPooling2D
    from keras.layers import Dense
    from keras.layers import Dropout
    from keras.layers import Flatten
    # create model
    model = Sequential()
    model.add(Conv2D(30, (5, 5), padding='valid', input_shape=(28, 28, 1), activation='relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Conv2D(15, (3, 3), activation='relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Dropout(0.2))
    model.add(Flatten())
    model.add(Dense(128, activation='relu'))
    model.add(Dense(50, activation='relu'))
    model.add(Dense(num_classes, activation='softmax'))
    # Compile model
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    return model
    
# Sample Standardization
def ejemplo04():

    from keras.datasets import mnist
    from keras.preprocessing.image import ImageDataGenerator
    from matplotlib import pyplot
    # load data
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # create a grid of 3x3 images
    for i in range(0, 9):
        pyplot.subplot(330 + 1 + i)
        pyplot.imshow(X_train[i], cmap=pyplot.get_cmap('gray'))
    # show the plot
    pyplot.show()
    # reshape to be [samples][pixels][width][height]
    X_train = X_train.reshape(X_train.shape[0], 28, 28, 1)
    X_test = X_test.reshape(X_test.shape[0], 28, 28, 1)
    # convert from int to float
    X_train = X_train.astype('float32')
    X_test = X_test.astype('float32')
    # define data preparation
    datagen = ImageDataGenerator(featurewise_center=False, samplewise_center=True,
    featurewise_std_normalization=False, samplewise_std_normalization=True)
    # fit parameters from data
    datagen.fit(X_train)
    # configure batch size
    iter = datagen.flow(X_train, y_train, batch_size=9)
    # retrieve one batch of images
    X_batch, y_batch = iter.next()
    # create a grid of 3x3 images
    for i in range(0, 9):
        pyplot.subplot(330 + 1 + i)
        pyplot.imshow(X_batch[i].reshape(28, 28), cmap=pyplot.get_cmap('gray'))
    # show the plot
    pyplot.show()
    
# Standardize images across the dataset, mean=0, stdev=1
def ejemplo05():
    from keras.datasets import mnist
    from keras.preprocessing.image import ImageDataGenerator
    from matplotlib import pyplot
    # load data
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # reshape to be [samples][pixels][width][height]
    X_train = X_train.reshape(X_train.shape[0], 28, 28, 1)
    X_test = X_test.reshape(X_test.shape[0], 28, 28, 1)
    # convert from int to float
    X_train = X_train.astype('float32')
    X_test = X_test.astype('float32')
    # define data preparation
    datagen = ImageDataGenerator(featurewise_center=True, featurewise_std_normalization=True)
    # fit parameters from data
    datagen.fit(X_train)
    # configure batch size
    iter = datagen.flow(X_train, y_train, batch_size=9)
    # retrieve one batch of images
    X_batch, y_batch = iter.next()
    # create a grid of 3x3 images
    for i in range(0, 9):
        pyplot.subplot(330 + 1 + i)
        pyplot.imshow(X_batch[i].reshape(28, 28), cmap=pyplot.get_cmap('gray'))
    # show the plot
    pyplot.show()
    
# ZCA whitening
def ejemplo06():  
    # ZCA whitening
    from keras.datasets import mnist
    from keras.preprocessing.image import ImageDataGenerator
    from matplotlib import pyplot
    # load data
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # reshape to be [samples][pixels][width][height]
    X_train = X_train.reshape(X_train.shape[0], 28, 28, 1)
    X_test = X_test.reshape(X_test.shape[0], 28, 28, 1)
    # convert from int to float
    X_train = X_train.astype('float32')
    X_test = X_test.astype('float32')
    # define data preparation
    datagen = ImageDataGenerator(featurewise_center=False, featurewise_std_normalization=False,
    zca_whitening=True)
    # fit parameters from data
    datagen.fit(X_train)
    # configure batch size
    iter = datagen.flow(X_train, y_train, batch_size=9)
    # retrieve one batch of images
    X_batch, y_batch = iter.next()
    # create a grid of 3x3 images
    for i in range(0, 9):
        pyplot.subplot(330 + 1 + i)
        pyplot.imshow(X_batch[i].reshape(28, 28), cmap=pyplot.get_cmap('gray'))
    # show the plot
    pyplot.show()

# Random Rotations
def ejemplo07():  
    from keras.datasets import mnist
    from keras.preprocessing.image import ImageDataGenerator
    from matplotlib import pyplot
    # load data
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # reshape to be [samples][pixels][width][height]
    X_train = X_train.reshape(X_train.shape[0], 28, 28, 1)
    X_test = X_test.reshape(X_test.shape[0], 28, 28, 1)
    # convert from int to float
    X_train = X_train.astype('float32')
    X_test = X_test.astype('float32')
    # define data preparation
    datagen = ImageDataGenerator(featurewise_center=False, featurewise_std_normalization=False,
    rotation_range=90)
    # fit parameters from data
    datagen.fit(X_train)
    # configure batch size
    iter = datagen.flow(X_train, y_train, batch_size=9)
    # retrieve one batch of images
    X_batch, y_batch = iter.next()
    # create a grid of 3x3 images
    for i in range(0, 9):
        pyplot.subplot(330 + 1 + i)
        pyplot.imshow(X_batch[i].reshape(28, 28), cmap=pyplot.get_cmap('gray'))
    # show the plot
    pyplot.show()
    
# Random Shifts
def ejemplo08():
    from keras.datasets import mnist
    from keras.preprocessing.image import ImageDataGenerator
    from matplotlib import pyplot
    # load data
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # reshape to be [samples][pixels][width][height]
    X_train = X_train.reshape(X_train.shape[0], 28, 28, 1)
    X_test = X_test.reshape(X_test.shape[0], 28, 28, 1)
    # convert from int to float
    X_train = X_train.astype('float32')
    X_test = X_test.astype('float32')
    # define data preparation
    shift = 0.2
    datagen = ImageDataGenerator(featurewise_center=False, featurewise_std_normalization=False,
    width_shift_range=shift, height_shift_range=shift)
    # fit parameters from data
    datagen.fit(X_train)
    # configure batch size
    iter = datagen.flow(X_train, y_train, batch_size=9)
    # retrieve one batch of images
    X_batch, y_batch = iter.next()
    # create a grid of 3x3 images
    for i in range(0, 9):
        pyplot.subplot(330 + 1 + i)
        pyplot.imshow(X_batch[i].reshape(28, 28), cmap=pyplot.get_cmap('gray'))
    # show the plot
    pyplot.show()
      
# Random Flips
def ejemplo09():
    # Random Flips
    from keras.datasets import mnist
    from keras.preprocessing.image import ImageDataGenerator
    from matplotlib import pyplot
    # load data
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # reshape to be [samples][pixels][width][height]
    X_train = X_train.reshape(X_train.shape[0], 28, 28, 1)
    X_test = X_test.reshape(X_test.shape[0], 28, 28, 1)
    # convert from int to float
    X_train = X_train.astype('float32')
    X_test = X_test.astype('float32')
    # define data preparation
    datagen = ImageDataGenerator(featurewise_center=False, featurewise_std_normalization=False,
    horizontal_flip=True, vertical_flip=True)
    # fit parameters from data
    datagen.fit(X_train)
    # configure batch size
    iter = datagen.flow(X_train, y_train, batch_size=9)
    # retrieve one batch of images
    X_batch, y_batch = iter.next()
    # create a grid of 3x3 images
    for i in range(0, 9):
        pyplot.subplot(330 + 1 + i)
        pyplot.imshow(X_batch[i].reshape(28, 28), cmap=pyplot.get_cmap('gray'))
    # show the plot
    pyplot.show()
    
# Save images
def ejemplo10():
    from keras.datasets import mnist
    from keras.preprocessing.image import ImageDataGenerator
    from matplotlib import pyplot
    from PIL import Image
    import os
    # load data
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    # reshape to be [samples][pixels][width][height]
    X_train = X_train.reshape(X_train.shape[0], 28, 28, 1)
    X_test = X_test.reshape(X_test.shape[0], 28, 28, 1)
    # convert from int to float
    X_train = X_train.astype('float32')
    X_test = X_test.astype('float32')
    # define data preparation
    datagen = ImageDataGenerator()
    # fit parameters from data
    datagen.fit(X_train)
    # configure batch size and save images to file
    os.makedirs('../cnn/images')
    iter = datagen.flow(X_train, y_train, batch_size=9, save_to_dir='../cnn/images', save_prefix='aug',save_format='png')
    # retrieve one batch of images
    X_batch, y_batch = iter.next()
    # create a grid of 3x3 images
    for i in range(0, 9):
        pyplot.subplot(330 + 1 + i)
        pyplot.imshow(X_batch[i].reshape(28, 28), cmap=pyplot.get_cmap('gray'))
    # show the plot
    pyplot.show()
    
# Simple CNN model for CIFAR-10
def ejemplo11():
    from keras.datasets import cifar10
    from keras.layers import Flatten
    from keras.constraints import maxnorm
    from keras.optimizers import SGD
    from keras.layers.convolutional import Convolution2D
    from keras.layers.convolutional import MaxPooling2D
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load data
    (X_train, y_train), (X_test, y_test) = cifar10.load_data()
    # normalize inputs from 0-255 to 0.0-1.0
    X_train = X_train.astype('float32')
    X_test = X_test.astype('float32')
    X_train = X_train / 255.0
    X_test = X_test / 255.0
    # one hot encode outputs
    y_train = np_utils.to_categorical(y_train)
    y_test = np_utils.to_categorical(y_test)
    num_classes = y_test.shape[1]
    # Create the model
    model = Sequential()
    model.add(Convolution2D(32, (3, 3), input_shape=(32, 32, 3), border_mode='same', activation='relu', W_constraint=maxnorm(3)))
    model.add(Dropout(0.2))
    model.add(Convolution2D(32, (3, 3), activation='relu', border_mode='same', W_constraint=maxnorm(3)))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Flatten())
    model.add(Dense(512, activation='relu', W_constraint=maxnorm(3)))
    model.add(Dropout(0.5))
    model.add(Dense(num_classes, activation='softmax'))
    # Compile model
    epochs = 25
    lrate = 0.01
    decay = lrate/epochs
    sgd = SGD(lr=lrate, momentum=0.9, decay=decay, nesterov=False)
    model.compile(loss='categorical_crossentropy', optimizer=sgd, metrics=['accuracy'])
    print(model.summary())
    # Fit the model
    model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=epochs, batch_size=32)
    # Final evaluation of the model
    scores = model.evaluate(X_test, y_test, verbose=0)
    print("Accuracy: %.2f%%" % (scores[1]*100))


# Larger CNN model for CIFAR-10
def ejemplo12():
    from keras.datasets import cifar10
    from keras.layers import Flatten
    from keras.constraints import maxnorm
    from keras.optimizers import SGD
    from keras.layers.convolutional import Convolution2D
    from keras.layers.convolutional import MaxPooling2D
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load data
    (X_train, y_train), (X_test, y_test) = cifar10.load_data()
    # normalize inputs from 0-255 to 0.0-1.0
    X_train = X_train.astype('float32')
    X_test = X_test.astype('float32')
    X_train = X_train / 255.0
    X_test = X_test / 255.0
    # one hot encode outputs
    y_train = np_utils.to_categorical(y_train)
    y_test = np_utils.to_categorical(y_test)
    num_classes = y_test.shape[1]
    # Create the model
    model = Sequential()
    model.add(Convolution2D(32, (3, 3), input_shape=(32, 32, 3), activation='relu', padding='same'))
    model.add(Dropout(0.2))
    model.add(Convolution2D(32, (3, 3), activation='relu', padding='same'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Convolution2D(64, (3, 3), activation='relu', padding='same'))
    model.add(Dropout(0.2))
    model.add(Convolution2D(64, (3, 3), activation='relu', padding='same'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Convolution2D(128, (3, 3), activation='relu', padding='same'))
    model.add(Dropout(0.2))
    model.add(Convolution2D(128, (3, 3), activation='relu', padding='same'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Flatten())
    model.add(Dropout(0.2))
    model.add(Dense(1024, activation='relu', kernel_constraint=maxnorm(3)))
    model.add(Dropout(0.2))
    model.add(Dense(512, activation='relu', kernel_constraint=maxnorm(3)))
    model.add(Dropout(0.2))
    model.add(Dense(num_classes, activation='softmax'))
    # Compile model
    epochs = 25
    lrate = 0.01
    decay = lrate/epochs
    sgd = SGD(lr=lrate, momentum=0.9, decay=decay, nesterov=False)
    model.compile(loss='categorical_crossentropy', optimizer=sgd, metrics=['accuracy'])
    print(model.summary())
    # Fit the model
    model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=epochs, batch_size=32)
    # Final evaluation of the model
    scores = model.evaluate(X_test, y_test, verbose=0)
    print("Accuracy: %.2f%%" % (scores[1]*100))
    
# MLP for the IMDB problem
def ejemplo13():
    import numpy
    from keras.datasets import imdb
    from keras.models import Sequential
    from keras.layers import Dense
    from keras.layers import Flatten
    from keras.layers.embeddings import Embedding
    from keras.preprocessing import sequence
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # load the dataset but only keep the top n words, zero the rest
    top_words = 5000
    (X_train, y_train), (X_test, y_test) = imdb.load_data(num_words=top_words)
    max_words = 500
    X_train = sequence.pad_sequences(X_train, maxlen=max_words)
    X_test = sequence.pad_sequences(X_test, maxlen=max_words)
    # create the model
    model = Sequential()
    model.add(Embedding(top_words, 32, input_length=max_words))
    model.add(Flatten())
    model.add(Dense(250, activation='relu'))
    model.add(Dense(1, activation='sigmoid'))
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    print(model.summary())
   # Fit the model
    model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=2, batch_size=128, verbose=2)
    # Final evaluation of the model
    scores = model.evaluate(X_test, y_test, verbose=0)
    print("Accuracy: %.2f%%" % (scores[1]*100))

# CNN for the IMDB problem
def ejemplo14():
    # CNN for the IMDB problem
    import numpy
    from keras.datasets import imdb
    from keras.models import Sequential
    from keras.layers import Dense
    from keras.layers import Flatten
    from keras.layers.convolutional import Conv1D
    from keras.layers.convolutional import MaxPooling1D
    from keras.layers.embeddings import Embedding
    from keras.preprocessing import sequence
    # fix random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    print("hola")
    # load the dataset but only keep the top n words, zero the rest
    top_words = 5000
    (X_train, y_train), (X_test, y_test) = imdb.load_data(num_words=top_words)
    # pad dataset to a maximum review length in words
    max_words = 500
    X_train = sequence.pad_sequences(X_train, maxlen=max_words)
    X_test = sequence.pad_sequences(X_test, maxlen=max_words)
    # create the model
    model = Sequential()
    model.add(Embedding(top_words, 32, input_length=max_words))
    model.add(Conv1D(filters=32, kernel_size=3, padding='same', activation='relu'))
    model.add(MaxPooling1D(pool_size=2))
    model.add(Flatten())
    model.add(Dense(250, activation='relu'))
    model.add(Dense(1, activation='sigmoid'))
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    print(model.summary())
    # Fit the model
    model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=2, batch_size=128, verbose=2)
    # Final evaluation of the model
    scores = model.evaluate(X_test, y_test, verbose=0)
    print("Accuracy: %.2f%%" % (scores[1]*100))


