'''
Created on 11 abr. 2019
@author: jfluna
Examples of the Jason Brownlee mini course of Computer vision
'''
# example of pixel normalization
def example01(img='../almacen/bondi_beach.jpg'):
    from numpy import asarray
    from PIL import Image
    
    print("Loading the image file:"+img)
    # load image
    image = Image.open(img)
    pixels = asarray(image)
    # confirm pixel range is 0-255
    print('Data Type: %s' % pixels.dtype)
    print('Min: %.3f, Max: %.3f' % (pixels.min(), pixels.max()))
    # convert from integers to floats
    pixels = pixels.astype('float32')
    print("Image normalization in range (0,1)")
    # normalize to the range 0-1
    pixels /= 255.0
    # confirm the normalization
    print('Min: %.3f, Max: %.3f' % (pixels.min(), pixels.max()))
   
# cnn with single convolutional, pooling and output layer    
def example02():
    from keras.models import Sequential
    from keras.layers import Conv2D
    from keras.layers import MaxPooling2D
    from keras.layers import Flatten
    from keras.layers import Dense
    # create model
    model = Sequential()
    # add convolutional layer
    model.add(Conv2D(32, (3,3), input_shape=(256, 256, 1)))
    model.add(MaxPooling2D())
    model.add(Flatten())
    model.add(Dense(1, activation='sigmoid'))
    model.summary()


# example of using a pre-trained model as a classifier
def example03(img='../almacen/dog.jpg'):
    from keras.preprocessing.image import load_img
    from keras.preprocessing.image import img_to_array
    from keras.applications.vgg16 import preprocess_input
    from keras.applications.vgg16 import decode_predictions
    from keras.applications.vgg16 import VGG16
    print('Loading image: '+img)
    image = load_img(img, target_size=(224, 224))
    # convert the image pixels to a numpy array
    image = img_to_array(image)
    # reshape data for the model
    image = image.reshape((1, image.shape[0], image.shape[1], image.shape[2]))
    # prepare the image for the VGG model
    image = preprocess_input(image)
    # load the model
    print('Loading pre-trained model: VGG16')
    model = VGG16()
    # predict the probability across all output classes
    yhat = model.predict(image)
    # convert the probabilities to class labels
    label = decode_predictions(yhat)
    # retrieve the most likely result, e.g. highest probability
    label = label[0][0]
    # print the classification
    print('%s (%.2f%%)' % (label[1], label[2]*100))
    
# fit a cnn on the fashion mnist dataset
def example04():
    from keras.datasets import fashion_mnist
    from keras.utils import to_categorical
    from keras.models import Sequential
    from keras.layers import Conv2D
    from keras.layers import MaxPooling2D
    from keras.layers import Dense
    from keras.layers import Flatten
    # load dataset
    print('Loading fashion_mnist dataset')
    (trainX, trainY), (testX, testY) = fashion_mnist.load_data()
    # reshape dataset to have a single channel
    trainX = trainX.reshape((trainX.shape[0], 28, 28, 1))
    testX = testX.reshape((testX.shape[0], 28, 28, 1))
    # convert from integers to floats
    trainX, testX = trainX.astype('float32'), testX.astype('float32')
    # normalize to range 0-1
    trainX,testX = trainX / 255.0, testX / 255.0
    # one hot encode target values
    trainY, testY = to_categorical(trainY), to_categorical(testY)
    # define model
    print('Creating the cnn model')
    model = Sequential()
    model.add(Conv2D(32, (3, 3), activation='relu', kernel_initializer='he_uniform',
    input_shape=(28, 28, 1)))
    model.add(MaxPooling2D())
    model.add(Flatten())
    model.add(Dense(100, activation='relu', kernel_initializer='he_uniform'))
    model.add(Dense(10, activation='softmax'))
    model.summary()
    model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])
    # fit model
    model.fit(trainX, trainY, epochs=10, batch_size=32, verbose=2)
    # evaluate model
    loss, acc = model.evaluate(testX, testY, verbose=0)
    print(loss, acc)

# example using image augmentation
def example05(img='../almacen/bird.jpg'):
    from numpy import expand_dims
    from keras.preprocessing.image import load_img
    from keras.preprocessing.image import img_to_array
    from keras.preprocessing.image import ImageDataGenerator
    from matplotlib import pyplot
    # load the image
    print('Loading image: '+img)
    img = load_img(img)
    # convert to numpy array
    data = img_to_array(img)
    # expand dimension to one sample
    samples = expand_dims(data, 0)
    # create image data augmentation generator
    datagen = ImageDataGenerator(horizontal_flip=True, vertical_flip=True, rotation_range=90)
    # prepare iterator
    it = datagen.flow(samples, batch_size=1)
    # generate samples and plot
    for i in range(9):
        # define subplot
        pyplot.subplot(330 + 1 + i)
        # generate batch of images
        batch = it.next()
        # convert to unsigned integers for viewing
        image = batch[0].astype('uint32')
        # plot raw pixel data
        pyplot.imshow(image)
    # show the figure
    pyplot.show()
    
# face detection with mtcnn on a photograph
def example06(img='../almacen/street.jpg'):
    from matplotlib import pyplot
    from matplotlib.patches import Rectangle
    from mtcnn.mtcnn import MTCNN
    # load image from file
    print('Loading image: '+img)
    pixels = pyplot.imread(img)
    # create the detector, using default weights
    detector = MTCNN()
    # detect faces in the image
    faces = detector.detect_faces(pixels)
    # plot the image
    pyplot.imshow(pixels)
    # get the context for drawing boxes
    ax = pyplot.gca()
    # get coordinates from the first face
    x, y, width, height = faces[0]['box']
    # create the shape
    print('printing a square over the recogniced face')
    rect = Rectangle((x, y), width, height, fill=False, color='green')
    # draw the box
    ax.add_patch(rect)
    # show the plot
    pyplot.show()