import { Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  public fechaInicio:string = "";
  public fechaFin:string = "";

  constructor() { }

  ngOnInit() {
  }

  eventoFechaI(message){
    console.log("Fecha inicio = "+message);
    this.fechaInicio = message;
  }

  eventoFechaF(message){
    console.log("Fecha fin = "+message);
    this.fechaFin = message;
  }

  //Codigo para gestionar la comunicacion del evento del boton
  // recibirFechas(message){
  //   console.log(message);
  // }
}
