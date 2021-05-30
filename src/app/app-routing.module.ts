import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DeliveryAgentComponent } from './delivery-agent/delivery-agent.component';
import { DisplayMessagesComponent } from './display-messages/display-messages.component';

const routes: Routes = [
  {path:'delivery',component: DeliveryAgentComponent},
  {path:'displayMessage',component:DisplayMessagesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
