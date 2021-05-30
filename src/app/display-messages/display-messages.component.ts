import { Component, Input, OnInit } from '@angular/core';
import { TransferService } from '../transfer.service';
import {DeliveryAgentComponent} from '../delivery-agent/delivery-agent.component'
@Component({
  selector: 'app-display-messages',
  templateUrl: './display-messages.component.html',
  styleUrls: ['./display-messages.component.css']
})
export class DisplayMessagesComponent implements OnInit {
  
  msgArray:Array<any>;
  constructor(private service:TransferService) { }
  
  ngOnInit(): void {
    this.service.getMessages().subscribe((data:any)=>{
      this.msgArray=data;
      console.log(this.msgArray[0]);
    });
  }

}
