import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DeliveryAgentComponent } from './delivery-agent/delivery-agent.component';
import { HttpClientModule } from '@angular/common/http';
import { DisplayMessagesComponent } from './display-messages/display-messages.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule}from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list'
import {MatSelectModule } from '@angular/material/select'
@NgModule({
  declarations: [
    AppComponent,
    DeliveryAgentComponent,
    DisplayMessagesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatListModule,
    MatSelectModule
    
  ],
  providers: [],
  bootstrap: [AppComponent],
  exports:[AppRoutingModule,DeliveryAgentComponent]
})
export class AppModule { }
