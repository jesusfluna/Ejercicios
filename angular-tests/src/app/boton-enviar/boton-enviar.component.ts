import { Component, OnInit, /*Output, EventEmitter, Input*/} from '@angular/core';

@Component({
  selector: 'app-boton-enviar',
  templateUrl: './boton-enviar.component.html',
  styleUrls: ['./boton-enviar.component.css']
})
export class BotonEnviarComponent implements OnInit {
  // @Input() horaInicio:string = "01/01/2001";
  // @Input() horaFin:string = "02/01/2001";
  // @Output() fechas:EventEmitter<string> = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  // enviarFechas(){
  //   this.fechas.emit(this.horaInicio+"|"+this.horaFin);
  // }
}