import { Component, OnInit } from '@angular/core';
//import * as CanvasJS from '../assets/canvasjs.min';
import * as CanvasJS from './canvasjs.min';

@Component({
  selector: 'app-grafica',
  templateUrl: './grafica.component.html',
  styleUrls: ['./grafica.component.css']
})
export class GraficaComponent implements OnInit {

  constructor() { }

  //Intial tests values, at the future we will use real database info.
  ngOnInit() {
  	let dataPoints = [];
	let y = 0;		
	for ( var i = 0; i < 10000; i++ ) {		  
		y += Math.round(5 + Math.random() * (-5 - 5));	
		dataPoints.push({ y: y});
	}
	let chart = new CanvasJS.Chart("chartContainer", {
		zoomEnabled: true,
		animationEnabled: true,
		exportEnabled: true,
		title: {
			text: " "
		},
		subtitles:[{
			text: "Results"
		}],
		data: [
		{
			type: "line",                
			dataPoints: dataPoints
		}]
	});
		
	chart.render();
  }

}