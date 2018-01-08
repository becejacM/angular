import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from "../../services/authentication.service";
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { ToastrService } from 'ngx-toastr';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css'],
  providers: [AuthenticationService]
})
export class AuthenticationComponent {

  private email: string;
  private password: string;
  loginForm;

  constructor(private autheticationService: AuthenticationService, private router: Router, private toastr: ToastrService,  private translate: TranslateService) {
    this.email = "";
    this.password = "";
  }


  ngOnInit() {

    this.loginForm = new FormGroup({
      email: new FormControl("", Validators.compose([Validators.required, Validators.minLength(5)])),
      password: new FormControl("", Validators.compose([Validators.required, Validators.minLength(3)]))
    })
  }


  authenticate(loginForm) {
    this.autheticationService.authenticateUser(Object.values(loginForm)[0], Object.values(loginForm)[1]).subscribe(
      data => {
        localStorage.setItem("loggedUser", JSON.stringify(data)),
        this.router.navigate(['/api/components']);
        if(this.translate.currentLang=="srb"){
          this.toastr.success('Ulogovani ste','Dobro dosli!');        
        }
        else{
          this.toastr.success('You are loged int','Welcome!');        
        }
      },
      error => this.toastr.error("Incorrect username and/or password"),
      () => console.log(JSON.parse(localStorage.getItem("loggedUser")))
    );
  }

}
