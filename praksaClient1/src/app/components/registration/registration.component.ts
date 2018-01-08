import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-services/user.service';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { FlashMessagesService } from 'ngx-flash-messages';
import { ToastrService } from 'ngx-toastr';
import {Router} from "@angular/router";
import { TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  addForm;
  
    constructor(private userService : UserService, private router: Router, private toastr:ToastrService, private translate: TranslateService) { 
  
      this.addForm = new FormGroup({
        name: new FormControl("", Validators.compose([Validators.required, Validators.minLength(4),Validators.maxLength(10)])),
        email: new FormControl("", Validators.compose([Validators.required])),
        password: new FormControl("", Validators.compose([Validators.required, Validators.minLength(5),Validators.maxLength(12)])),
        repeatedPassword: new FormControl("", Validators.compose([Validators.required, Validators.minLength(5),Validators.maxLength(12)]))
      });
    }
  
    ngOnInit() {
    }
  
    add(userDto){
      this.userService.register(Object.values(userDto)[0],Object.values(userDto)[1],Object.values(userDto)[2],Object.values(userDto)[3]).subscribe(
        data => {  
          this.router.navigate(['/api/login']);
          if(this.translate.currentLang=="srb"){
            this.toastr.success('Zdravo!', 'Uspesno ste se registrovali! Potvrdite svoju registraciju klikom na link, koji Vam je poslat na email!');
          }
          else{
            this.toastr.success('Hello!', 'You successfuly register to our application! Confirm your registration by accepting link on your email!');
          }
          
        },
        error => this.toastr.error("Something went wrong"),
      );
    }

  }