import { Component, OnInit } from '@angular/core';
import { LoggedUtils } from "../../utils/logged.utils"
import { FlashMessagesService } from 'ngx-flash-messages';
import { Router } from "@angular/router";
import { ToastrService } from 'ngx-toastr'
import { NgxPermissionsService } from 'ngx-permissions';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

  private perm;


  constructor(private router: Router, private toastr: ToastrService, private permissionsService: NgxPermissionsService, private translate: TranslateService) {
    translate.setDefaultLang('en');
  }

  ngOnInit() {
    const perm = [];
    perm.push(LoggedUtils.getRole());
    this.permissionsService.loadPermissions(perm);

    this.permissionsService.permissions$.subscribe((perm) => {
    })
  }


  logoutf() {
    LoggedUtils.clearLocalStorage();
    this.router.navigate(["/api/login"]);
    if(this.translate.currentLang=="srb"){
      this.toastr.success('Izlogovani ste!');        
    }
    else{
      this.toastr.success('You are loged out!');        
    }
    this.permissionsService.flushPermissions();
  }

  switchLanguage(language: string) {
    this.translate.use(language);
  }
}
