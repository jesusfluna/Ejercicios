import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
//npm install --save @angular/material @angular/animations @angular/cdk
import { MatDatepickerModule,
  MatNativeDateModule,
  MatFormFieldModule,
  MatInputModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CampoFechaComponent } from './campo-fecha/campo-fecha.component';
import { MenuComponent } from './menu/menu.component';
import { BotonEnviarComponent } from './boton-enviar/boton-enviar.component';
import { GraficaComponent } from './grafica/grafica.component';


@NgModule({
  declarations: [
    AppComponent,
    CampoFechaComponent,
    MenuComponent,
    BotonEnviarComponent,
    GraficaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  exports: [
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatInputModule,
    BrowserAnimationsModule]
})
export class AppModule { }
