'''
Created on 10 abr. 2019
@author: jfluna
'''
#------------------------ Importaciones ------------------------
import numpy as np
from keras.models import Model, Sequential
from keras.layers import Convolution2D, ZeroPadding2D, MaxPooling2D, Flatten, Dropout, Activation
from keras.preprocessing.image import load_img, img_to_array
from keras.applications.imagenet_utils import preprocess_input
from os import listdir
#---------------------------------------------------------------



'''
Creacion del modelo tipo loadVggFace e importado los pesos del entrenamiento vgg_face_weights.h5
para crear nuestra CNN lista para hacer las predicciones

IMPORTANTE!! no esta incluido en el proyecto el fichero vgg_face_weights.h5
Descarga => https://drive.google.com/file/d/1CPSeum3HpopfomUEK1gybeuIVoeJT_Eo/view
'''
def loadVggFaceModel(ruta='vgg_face_weights.h5'):
    model = Sequential()
    model.add(ZeroPadding2D((1,1),input_shape=(224,224, 3)))
    model.add(Convolution2D(64, (3, 3), activation='relu'))
    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(64, (3, 3), activation='relu'))
    model.add(MaxPooling2D((2,2), strides=(2,2)))

    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(128, (3, 3), activation='relu'))
    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(128, (3, 3), activation='relu'))
    model.add(MaxPooling2D((2,2), strides=(2,2)))

    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(256, (3, 3), activation='relu'))
    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(256, (3, 3), activation='relu'))
    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(256, (3, 3), activation='relu'))
    model.add(MaxPooling2D((2,2), strides=(2,2)))

    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(512, (3, 3), activation='relu'))
    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(512, (3, 3), activation='relu'))
    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(512, (3, 3), activation='relu'))
    model.add(MaxPooling2D((2,2), strides=(2,2)))

    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(512, (3, 3), activation='relu'))
    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(512, (3, 3), activation='relu'))
    model.add(ZeroPadding2D((1,1)))
    model.add(Convolution2D(512, (3, 3), activation='relu'))
    model.add(MaxPooling2D((2,2), strides=(2,2)))

    model.add(Convolution2D(4096, (7, 7), activation='relu'))
    model.add(Dropout(0.5))
    model.add(Convolution2D(4096, (1, 1), activation='relu'))
    model.add(Dropout(0.5))
    model.add(Convolution2D(2622, (1, 1)))
    model.add(Flatten())
    model.add(Activation('softmax'))
    
    model.load_weights(ruta)
    vgg_face_descriptor = Model(inputs=model.layers[0].input, outputs=model.layers[-2].output)
    return vgg_face_descriptor

'''
Normalizacion de una imagen con valores comprendidos entre -1 y 1
'''
def preprocess_image(image_path):
    img = load_img(image_path, target_size=(224, 224)) # Carga y redimension de la imagen
    img = img_to_array(img) # Transformacion de la imagen a un array de pixels
    img = np.expand_dims(img, axis=0) # Añadimos una 'dimension' a nuestro array en la posicion 0
    img = preprocess_input(img)# Normalizar los valores entre -1 y 1
    return img

'''
devuelve un color en formato RGB
'''
def getColor(color=1):
    res = (36,231,17)
    
    if(color!=1):
        res = (67,67,67)
    return res

'''
Funcion coseno para calcular la similtud entre dos imagenes previamente manipuladas 
'''
def calcularSimilitud(source_representation, test_representation):
    a = np.matmul(np.transpose(source_representation), test_representation)
    b = np.sum(np.multiply(source_representation, source_representation))
    c = np.sum(np.multiply(test_representation, test_representation))
    return 1 - (a / (np.sqrt(b) * np.sqrt(c)))

'''
Diccionario de datos con k = prediccion v = nombre por cada imagen del directorio
'''
def bdRostros(ruta,model):
    employees = dict()
    
    for file in listdir(ruta):
        employee = file.split(".")[0]
        employees[employee] = model.predict(preprocess_image(ruta+'%s.jpg' % (employee)))[0,:]
        
    return employees