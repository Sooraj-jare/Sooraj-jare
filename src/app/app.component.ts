import { Component, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { DeliveryAgentComponent } from './delivery-agent/delivery-agent.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Credit Suisse Internal Transfer';
  constructor( private route:Router) { 
  }

  delivery():void{
    this.route.navigate(['/delivery']);

  }

  displayMsg(){
    this.route.navigate(['/displayMessage']);
  }
}
