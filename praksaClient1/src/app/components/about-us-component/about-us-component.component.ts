import { Component, OnInit } from '@angular/core';
//import { AgmCoreModule } from '@agm/core';

@Component({
  selector: 'app-about-us-component',
  templateUrl: './about-us-component.component.html',
  styleUrls: ['./about-us-component.component.css']
})
export class AboutUsComponentComponent implements OnInit {

  lat: number = 45.257239;
  lng: number = 19.844366;
  
  //45.245715
  //19.851624
  constructor() { }

  ngOnInit() {
  }

  changeLocation(latitude, langitude){
    this.lat = latitude;
    this.lng = langitude;
  }
  
  
}
