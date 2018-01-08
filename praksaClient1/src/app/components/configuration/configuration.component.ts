import { Router } from '@angular/router';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ComponentService } from '../../services/component-services/component.service';
import { ConfigurationService } from '../../services/configuration-services/configuration.service'
import { MyComponent } from '../../my-component'
import { Address } from '../../address'
import { MyConfiguration } from '../../my-configuration'
import { MyType } from '../../my-type'
import { MyComponentComponent } from '../../components/my-component/my-component.component';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoggedUtils } from '../../utils/logged.utils';
import { NgxPermissionsService } from 'ngx-permissions';
import { ToastrService } from 'ngx-toastr';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent implements OnInit {

  allComponents: MyComponent[];

  addConfForm;
  name: string = ""
  updateName: string;
  typesCombo: string = "";

  clickedButton: string;

  types: MyType[];
  requiredTypes: MyType[];

  components: MyComponent[];
  addedComponents: MyComponent[] = [];

  myConfigurations: MyConfiguration[];
  clickedConfiguration: MyConfiguration;

  pages: number[];
  currentPage: number;
  role: string;
  userId: number;

  checkOutForm;
  cityPay: string = ""
  cityDeliver: string;
  streetPay: string = ""
  streetDeliver: string;
  zipCodePay: string = ""
  zipCodeDeliver: string;
  countryPay: string = ""
  countryDeliver: string;
  payment: string;

  constructor(private router: Router, private componentService: ComponentService, private configurationService: ConfigurationService,
    private permissionsService: NgxPermissionsService, private toastr: ToastrService,  private translate: TranslateService) {
  }


  ngOnInit() {
    this.addConfForm = new FormGroup({
      name: new FormControl(),
      typesCombo: new FormControl(),
      updateName: new FormControl()
    });

    this.checkOutForm = new FormGroup({
      streetDeliver: new FormControl(),
      cityDeliver: new FormControl(),
      zipCodeDeliver: new FormControl(),
      countryDeliver: new FormControl(),
      streetPay: new FormControl(),
      cityPay: new FormControl(),
      zipCodePay: new FormControl(),
      countryPay: new FormControl(),
    });

    this.componentService.getComponents().then(components => {
      this.allComponents = components;
    }).catch(response => {
      this.toastr.error(response._body);
    });

    let permission = [];
    this.permissionsService.flushPermissions();

    this.role = LoggedUtils.getRole();
    permission.push(this.role);
    this.permissionsService.loadPermissions(permission);
    if (this.role == "[USER]")
      this.myConfiguratons(0);

  }

  changePageViewComponents(type, numOfPage) {
    this.typeChange(type, numOfPage);
  }

  addConfiguration(name) {

    if (!this.validRequiredComponents()) {
      return;
    }
    let configuration = new MyConfiguration(name, this.addedComponents, 4);

    this.configurationService.addNewConfiguration(configuration).then(conf => {

      if(this.translate.currentLang=="srb"){
        this.toastr.success('Konfiguracija uspesno dodata!');        
      }
      else{
        this.toastr.success('Configuration successfully added!');        
      }
      this.clickedConfiguration = conf;
    }).catch(response => {
      this.toastr.error(response._body);
    });
    this.myConfigurations.push(configuration);
    this.name = "";
    this.typesCombo = "";
    this.addedComponents = [];
    this.clickedConfiguration = configuration;
    let address = LoggedUtils.getAddress();
    this.checkOutForm = new FormGroup({
      streetDeliver: new FormControl(address.street),
      cityDeliver: new FormControl(address.city),
      zipCodeDeliver: new FormControl(address.zipCode),
      countryDeliver: new FormControl(address.country),
      streetPay: new FormControl(address.street),
      cityPay: new FormControl(address.city),
      zipCodePay: new FormControl(address.zipCode),
      countryPay: new FormControl(address.country),
    });
    this.fillFieldsForCheckOut();
  }


  validRequiredComponents(): boolean {
    let exist;
    let valid = true;
    let requiredTypesString = "";
    this.requiredTypes.forEach(type => {
      exist = false;
      if (this.addedComponents.length != 0) {
        this.addedComponents.forEach(component => {
          if (type['name'] == component['type'].name)
            exist = true;
        });
        if (!exist) {
          requiredTypesString += type['name'] + ",";
          valid = false;
        }
      } else {
        requiredTypesString += " " + type['name'] + ",";
        valid = false;
      }
    });
    if (valid)
      return true;
    else {
      requiredTypesString = requiredTypesString.substring(0, requiredTypesString.length - 1);
      if(this.translate.currentLang=="srb"){
        this.toastr.warning('You have to choose following types: ' + requiredTypesString);        
      }
      else{
        this.toastr.warning('Morate odabrati sledece tipove: ' + requiredTypesString);        
      }
      return false;
    }
  }

  deleteConfiguration(confId) {
    let ind;
    for (var index = 0; index < this.myConfigurations.length; index++) {
      if (this.myConfigurations[index]['id'] == confId)
        ind = index;

    }
    this.configurationService.deleteConfiguration(confId).then(response => {
      this.myConfigurations.splice(ind, 1);
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Konfiguracija uspesno obrisana!');        
      }
      else{
        this.toastr.success('Configuration successfully deleted!');        
      }
    }).catch(response => {
      this.toastr.error(response._body);
    });
  }

  adminDeleteConfiguration(confId, idUser) {
    let ind;
    for (var index = 0; index < this.myConfigurations.length; index++) {
      if (this.myConfigurations[index]['id'] == confId)
        ind = index;
    }
    this.configurationService.adminDeleteConfiguration(confId, idUser).then(response => {
      this.myConfigurations.splice(ind, 1);
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Konfiguracija uspesno obrisana!');        
      }
      else{
        this.toastr.success('Configuration successfully deleted!');        
      }
    }).catch(response => {
      this.toastr.error(response._body);
    });
  }

  updateConfiguration(newName) {
    if (!this.validRequiredComponents()) {
      return;
    }

    this.clickedConfiguration.name = Object.values(newName)[0];
    this.clickedConfiguration.userId = 4;
    this.clickedConfiguration['components'] = this.addedComponents;
    this.configurationService.updateConfiguration(this.clickedConfiguration).then(result => {
      if (this.clickedButton == "")
        this.changePageMyConfigurations(this.currentPage);
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Konfiguracija uspesno izmenjena!');        
      }
      else{
        this.toastr.success('Configuration successfully updated!');        
      }
    }).catch(response => {
      this.toastr.error(response._body);
    });
    this.clickedButton = "";
  }

  enabled(conf): boolean {
    if (conf.configurationStatus != "CREATED")
      return false;
    else
      return true;
  }

  typeChange(selectedType, numOfPage) {
    this.componentService.searchByType(selectedType.name, numOfPage).then(components => {
      components['content'].forEach(c => {
        this.allComponents.forEach(component => {
          if (c['id'] == component['id']) {
            c['quantity'] = component['quantity'];

          }
        });



        this.components = components['content'];
        this.pages = Array(components['totalPages']).fill(0).map((x, i) => i);
        this.currentPage = components['number'];


        if (this.clickedButton != 'update')
          this.updateQuantity();

      });
    });


  }

  updateQuantity() {
    this.addedComponents.forEach(addedComponent => {
      this.components.forEach(component => {
        if (addedComponent['id'] == component['id']) {
          component['quantity'] = component['quantity'] - 1;
        }
      });
    });
  }

  addComponent(component) {
    let exist: boolean;
    if (component.type.cardinality == '1') {
      this.addedComponents.forEach(c => {
        if (c['type'].name == component.type.name) {
          exist = true;
        }
      });
    }
    if (exist) {
      if(this.translate.currentLang=="srb"){
        this.toastr.error('Ova komponenta ne moze biti vise od jednom dodata u istu konfiguraciju!');        
      }
      else{
        this.toastr.error('This component can not be more then once in the same configuration!');        
      }
      return;
    }

    this.addedComponents.push(component)
    this.components.forEach(c => {
      if (component.id == c['id']) {
        c['quantity']--;
      }
    })
  }

  deleteComponent(component) {
    for (let i = 0; i < this.addedComponents.length; i++) {
      if (component.id == this.addedComponents[i]['id']) {
        this.addedComponents.splice(i, 1);
        break;
      }
    };
    this.allComponents.forEach(c => {
      if (component.id == c['id']) {
        c['quantity']++;
      }
    })
    if (this.components != undefined) {
      this.components.forEach(c => {
        if (component.id == c['id']) {
          c['quantity']++;
        }
      });
    }
  }

  valid(component): boolean {

    if (component.quantity != 0)
      return false;
    else
      return true;
  }

  readTypes() {
    this.componentService.getAllTypes().then(types => this.types = types);
    this.componentService.getRequiredComponents().then(requiredTypes => this.requiredTypes = requiredTypes)
    this.clickedButton = 'add';
    this.addedComponents = [];
  }

  myConfiguratons(page) {
    this.configurationService.getMyConfigurations(page).then(myConfigurations => {
      this.myConfigurations = myConfigurations['content'];
      this.pages = Array(myConfigurations['totalPages']).fill(0).map((x, i) => i);
    });
    this.clickedButton = 'view';
  }

  allConfiguratons(page) {
    this.configurationService.getAllConfigurations(page).then(myConfigurations => {
      this.myConfigurations = myConfigurations['content'];
      this.pages = Array(myConfigurations['totalPages']).fill(0).map((x, i) => i);
    });
    this.clickedButton = 'view';
  }

  viewComponents(configuration) {
    this.clickedConfiguration = configuration;
    this.clickedButton = 'viewComponents';
  }

  updateConfClicked(configuration) {
    this.clickedConfiguration = configuration;
    this.readTypes();
    this.clickedButton = 'update';
    this.typeChange(this.clickedConfiguration.components[0]['type'].name, 0)
    this.addConfForm = new FormGroup({
      updateName: new FormControl(this.clickedConfiguration['name']),
      name: new FormControl(),
      typesCombo: new FormControl(),
    })
    this.addedComponents = this.clickedConfiguration['components'];
  }

  checkOut(form) {
    let values = Object.values(form);
    let payAddress = new Address(values[0], values[1], values[2], values[3])
    let deliverAddress = new Address(values[4], values[5], values[6], values[7])
    this.clickedConfiguration.payAddress = payAddress;
    this.clickedConfiguration.deliverAddress = deliverAddress;
    this.clickedConfiguration.methodOfPayment = values[8];
    this.configurationService.checkOut(this.clickedConfiguration).then(conf => {
      this.changePageMyConfigurations(this.currentPage);
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Konfiguracija je potvrdjena!');        
      }
      else{
        this.toastr.success('Configuration status set to checked out!');        
      }
      
    }).catch(response => {
      this.toastr.error(response._body);
    })
  }

  checkOutClicked(conf) {
    this.clickedConfiguration = conf;
    this.fillFieldsForCheckOut();
  }

  deliver(configuration) {
    this.clickedConfiguration = configuration;
    this.configurationService.deliver(configuration).then(conf => {
      this.changePageAllConfigurations(this.currentPage);
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Konfiguracija je placena!');        
      }
      else{
        this.toastr.success('Configuration status set to paid!');        
      }
    }).catch(response => {
      this.toastr.error(response._body);
    });
  }

  pay(configuration) {
    this.clickedConfiguration = configuration;
    this.configurationService.pay(configuration).then(conf => {
      this.changePageAllConfigurations(this.currentPage);
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Konfiguracija je dostavljena!');        
      }
      else{
        this.toastr.success('Configuration status set to delivered!');        
      }
    }).catch(response => {
      this.toastr.error(response._body);
    });
  }

  changePageMyConfigurations(page) {
    this.currentPage = page;
    this.myConfiguratons(page);
  }

  changePageAllConfigurations(page) {
    this.currentPage = page;
    this.allConfiguratons(page);
  }

  setModeToView() {
    this.myConfiguratons(0);
  }

  fillFieldsForCheckOut() {
    this.clickedButton = "checkOut";
    let address = LoggedUtils.getAddress();
    this.checkOutForm = new FormGroup({
      streetDeliver: new FormControl(address.street),
      cityDeliver: new FormControl(address.city),
      zipCodeDeliver: new FormControl(address.zipCode),
      countryDeliver: new FormControl(address.country),
      streetPay: new FormControl(address.street),
      cityPay: new FormControl(address.city),
      zipCodePay: new FormControl(address.zipCode),
      countryPay: new FormControl(address.country),
      payment: new FormControl("Credit card"),
    });
  }
}
