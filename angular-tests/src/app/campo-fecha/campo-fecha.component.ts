import { Component, Input, Output, EventEmitter} from '@angular/core';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';

@Component({
  selector: 'app-campo-fecha',
  templateUrl: './campo-fecha.component.html',
  styleUrls: ['./campo-fecha.component.css']
})
export class CampoFechaComponent{
  @Input() texto:string;
  @Output() salida:EventEmitter<string> = new EventEmitter<string>();
  
  constructor(){}

  addEvent(event: MatDatepickerInputEvent<Date>) {
    this.salida.emit(event.value.toDateString());
  }

}
