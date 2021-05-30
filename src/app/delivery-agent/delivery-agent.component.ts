import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TransferService } from '../transfer.service';

@Component({
  selector: 'app-delivery-agent',
  templateUrl: './delivery-agent.component.html',
  styleUrls: ['./delivery-agent.component.css']
})
export class DeliveryAgentComponent implements OnInit {

  deliveryForm:FormGroup;
  success:boolean=false;
  fail:boolean=false;
  msgArrayDeliver:Array<any>;
  ifscCodes:Array<any>=["CRESCHZZ80A","CRESCHZZ80O","CRESCHZZ80H","CRESCHZZ80Z","CRESCHZZ80F","CRESCHZZ80B"];
  branchName:Array<any>=["Zurich","Galleria","Seefeld","Wiedikon","Oerlikon","Aussersihl"];
  client:Array<any>=["CREDIT SUISSE FUND SERVICES (LUXEMBOURG) SA",
                      "BROWN BROTHERS HARRIMAN AND CO.",
                      "EUROCLEAR BANK S.A / N.V",
                      "CREDIT SUISSE (UK) LIMITED",
                      "CREDIT SUISSE (SCHWEIZ) AG",
                      "SIX SIS AG"
                    ];
  clientCode:Array<any>=["CSAMLULLXXX","BBHCUS33XXX","MGTCBEBEFND","CSUKGB2LXXX","CRESCHZZ80A","INSECHZZXXX"];
  constructor( private formBuilder: FormBuilder,private service:TransferService) { }

  ngOnInit(): void {
    
    this.deliveryForm = this.formBuilder.group({
      deliveringAgent: ['', Validators.required],
      seller: ['',Validators.required],
      safekeepingAccount: ['',Validators.required],
      placeOfSettlement: ['', [Validators.required]],
      placeOfSafekeepingAccount:['',Validators.required]
  });

  }
  onSubmit(){
    this.service.makeTransaction(this.deliveryForm.value).subscribe((data:boolean)=>{
      console.log(data);
      if(data==true){
       this.success=true;
       this.deliveryForm.reset;
      }else{
        this.fail=true;
      }
     });

  }
}
