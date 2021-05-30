import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DeliveryModel } from './delivery-model';

@Injectable({
  providedIn: 'root'
})
export class TransferService {
  private baseUrl = 'http://localhost:8080/';
  constructor(private http:HttpClient) {
  }
 
  makeTransaction(delivery: DeliveryModel){ 
    console.log(delivery.deliveringAgent);
    return this.http.post(`${this.baseUrl}`+'publishToQueue', delivery);
  }  

  getMessages(){
    return this.http.get(`${this.baseUrl}`+'getMessage')
  }

}
