import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
//import { AgmCoreModule } from '@agm/core';
// <reference path="node_modules/bingmaps/scripts/MicrosoftMaps/Microsoft.Maps.All.d.ts" />
//import { MapModule, MapAPILoader, BingMapAPILoaderConfig, BingMapAPILoader, WindowRef, DocumentRef, MapServiceFactory, BingMapServiceFactory } from "angular-maps";
import { AgmCoreModule } from 'angular2-google-maps/core';


import { AppComponent } from './app.component';
import { UserComponent } from './components/user/user.component';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import { MyComponentComponent } from './components/my-component/my-component.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { AboutUsComponentComponent } from './components/about-us-component/about-us-component.component';

import { AuthenticationService } from './services/authentication.service';
import { ComponentService } from './services/component-services/component.service';
import { ConfigurationService } from './services/configuration-services/configuration.service';
import { UserService } from './services/user-services/user.service';
import { ManufacturerService } from './services/manufacturer-service/manufacturer.service';

import { FlashMessagesModule } from 'ngx-flash-messages';
import { AuthenticationComponent } from './components/authentication/authentication.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegistrationComponent } from './components/registration/registration.component';
import { NgxPermissionsModule } from 'ngx-permissions';
import { HttpClient } from '@angular/common/http';

import { HttpClientModule } from '@angular/common/http';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

const googleMapsCore = AgmCoreModule.forRoot({
  apiKey : 'AIzaSyCFzvbaWDsf_9WWtR3dOWm4Iz8qvnHqGxg',
});
const appRoutes: Routes = [

]

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    ConfigurationComponent,
    MyComponentComponent,
    AuthenticationComponent,
    NavbarComponent,
    RegistrationComponent,
    NavbarComponent,
    HomepageComponent,
    AboutUsComponentComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpModule,
    FlashMessagesModule,
    googleMapsCore,
    ToastrModule.forRoot(),
    NgxPermissionsModule.forRoot(),
    RouterModule.forRoot(

      [
        { path: '', component: HomepageComponent }, 
        { path: 'api/login', component: AuthenticationComponent },
        { path: 'api/users', component: UserComponent },
        { path: 'api/components', component: MyComponentComponent },
        { path: 'api/configurations', component: ConfigurationComponent },
        { path: 'api/registration', component: RegistrationComponent},
        { path: 'api/about-us', component: AboutUsComponentComponent }]

    ),
    NgxPermissionsModule.forRoot(),
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
    
    
  ],
  providers: [ AuthenticationService, ComponentService, ConfigurationService, UserService, HttpClient, ManufacturerService
    ],
  bootstrap: [AppComponent]
})
export class AppModule {
  l

}

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
//AIzaSyCFzvbaWDsf_9WWtR3dOWm4Iz8qvnHqGxg
