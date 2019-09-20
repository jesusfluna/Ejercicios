'''
Created on 10 abr. 2019
@author: jfluna

Based on this article https://sefiks.com/2018/08/06/deep-face-recognition-with-keras/ of Sefik Ilkin Serengil
'''
#------------------------ Importaciones ------------------------
import default.SecurityCam as sc
import numpy as np
import cv2
from keras.preprocessing import image
#---------------------------------------------------------------



#--------------------Preparacion de predicciones----------------
#Cargamos el modulo de CV2 para deteccion de rstros
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_frontalface_default.xml")
'''
IMPORTANTE!! no esta incluido en el proyecto el fichero vgg_face_weights.h5
Descarga => https://drive.google.com/file/d/1CPSeum3HpopfomUEK1gybeuIVoeJT_Eo/view
'''
#Cargamos la red neuronal ya entrenada para realizar las predicciones
model = sc.loadVggFaceModel()
#Ruta del directorio con las imagenes del personal a detectar
employees = sc.bdRostros("../imagenes/",model)
#---------------------------------------------------------------



#----------------- Captura de imagen y deteccion ---------------
cap = cv2.VideoCapture(0) # Activacion de la cam

while(True): # Bucle infinito
    ret, img = cap.read() # Captura de la imagen de la webcam

    faces = face_cascade.detectMultiScale(img, 1.3, 5) # Detectamos los rostros de la imagen
    
    for (x,y,w,h) in faces: #por cada rostro
        if w > 130: 
            detected_face = img[int(y):int(y+h), int(x):int(x+w)] # Cortamos la imagen para quedarnos solo con el rostro
            detected_face = cv2.resize(detected_face, (224, 224)) # Redimensionamos la imagen a tamaño 224*224
            
            img_pixels = image.img_to_array(detected_face) # Transformamos la imagen a un array
            img_pixels = np.expand_dims(img_pixels, axis = 0) # Añadimos una 'dimension' en la posicion 0
            img_pixels /= 255 # Normalizamos los valores de la imagen
            
            captured_representation = model.predict(img_pixels)[0,:] # Obtenemos el valor de prediccion para el rostro
            
            found = 0
            for i in employees:
                employee_name = i # Nombre de la persona de la imagen
                representation = employees[i] # Valor de prediccion iterada
                
                similarity = sc.calcularSimilitud(representation, captured_representation) # calcular la similut entre ambos rostros
                if(similarity < 0.33):
                    cv2.putText(img, employee_name, (int(x+w+15), int(y-12)), cv2.FONT_HERSHEY_SIMPLEX, 1, sc.getColor(1), 2)
                    
                    found = 1
                    break
                    
            if(found == 1):
                #dibujo de union entre el texto y el rostro color verde
                cv2.line(img,(int((x+x+w)/2),y+15),(x+w,y-20),sc.getColor(1),1)
                cv2.line(img,(x+w,y-20),(x+w+10,y-20),sc.getColor(1),1)
            else:  
                #dibujo de union entre el texto y el rostro color negro 
                cv2.line(img,(int((x+x+w)/2),y+15),(x+w,y-20),sc.getColor(0),1)
                cv2.line(img,(x+w,y-20),(x+w+10,y-20),sc.getColor(0),1)
                cv2.putText(img, 'unknown', (int(x+w+15), int(y-12)), cv2.FONT_HERSHEY_SIMPLEX, 1, sc.getColor(0), 2)
    
    cv2.imshow('SecutiryCam',img) # Mostramos la imagen ya procesada por pontalla
    
    if cv2.waitKey(1) & 0xFF == ord('q'): # Salida del bucle al pulsar la tecla 'q'
        break
    
# cierre de los objetos usados     
cap.release()
cv2.destroyAllWindows()

#---------------------------------------------------------------