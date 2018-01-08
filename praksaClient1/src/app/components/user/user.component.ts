import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-services/user.service';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { FlashMessagesService } from 'ngx-flash-messages';
import { ToastrService } from 'ngx-toastr';
import { Address } from '../../address';
import { LoggedUtils } from "../../utils/logged.utils"
import {Router} from "@angular/router";
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  updateForm;
  emailFormData = LoggedUtils.getEmail();
  changePasswordForm;
  EMAIL_REGEXP = "[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])+(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*";

  constructor(private userService : UserService, private router: Router, private toastr:ToastrService, private translate: TranslateService) { 

    this.updateForm = new FormGroup({
      username: new FormControl(LoggedUtils.getUsername(), Validators.compose([Validators.required, Validators.minLength(5)])),
      email: new FormControl(LoggedUtils.getEmail(), Validators.compose([Validators.required,Validators.pattern(this.EMAIL_REGEXP)]))
    });

    this.changePasswordForm = new FormGroup({
      
      oldPassword: new FormControl("", Validators.compose([Validators.required, Validators.minLength(3),Validators.maxLength(12)])),
      newPassword: new FormControl("", Validators.compose([Validators.required, Validators.minLength(3),Validators.maxLength(12)])),
      repeatNewPassword: new FormControl("", Validators.compose([Validators.required, Validators.minLength(3),Validators.maxLength(12)]))
    });
  }

  ngOnInit() {
  }


  updateProfile(updatedUser){
    this.userService.updateProfile(Object.values(updatedUser)).subscribe(
      data => {  
        LoggedUtils.setEmail(data.email);
        if(this.translate.currentLang=="srb"){
          this.toastr.success('Vas profil je izmenjen!');        
        }
        else{
          this.toastr.success('Your profile is updated!');        
        }


      },
      error => this.toastr.error("Something went wrong"),
    );
  }

  changePassword(changePassObj){
    this.userService.changePassword(Object.values(changePassObj)).subscribe(
      data => {  
        this.router.navigate(['/api/login']),
        this.toastr.success('Hello!', 'You successfuly changed your password, login to continue!');
        if(this.translate.currentLang=="srb"){
          this.toastr.success('Uspesno ste promenili svoju sifru! Ulogujte se za nastavak');        
        }
        else{
          this.toastr.success('You successfuly changed your password, login to continue!');        
        }
        LoggedUtils.clearLocalStorage();
      },
      error => this.toastr.error("Something went wrong"),
    );
  }
  

}
export interface User{
  name:string,
  email:string,
  role:string,
  id:number,
  configurations:Array<any>,
  address: Address;
}