import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MyComponent } from '../../my-component';
import { MyType } from '../../my-type';
import { MyManufacturer } from '../../my-manufacturer';
import { MyComment } from '../../my-comment';
import { User } from '../user/user.component';
import { ComponentService } from '../../services/component-services/component.service';
import { ManufacturerService } from '../../services/manufacturer-service/manufacturer.service';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { FlashMessagesService } from 'ngx-flash-messages';
import { ToastrService } from 'ngx-toastr';
import { LoggedUtils } from '../../utils/logged.utils';
import { NgxPermissionsService } from 'ngx-permissions';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-my-component',
  templateUrl: './my-component.component.html',
  styleUrls: ['./my-component.component.css']
})
export class MyComponentComponent implements OnInit {
  components: MyComponent[];
  types: MyType[];
  manufacturers: MyManufacturer[];
  comments: MyComment[];

  fromValue: string;
  toValue: string;
  checkboxValue: boolean;
  typeValue: string;
  paramPrice: string;

  searchForm;
  addForm;
  updateForm;
  addCommentForm;
  newTypeForm;
  newManufacturerForm;

  id: any;
  name: string;
  type: MyType;
  manufacturer: MyManufacturer;
  quantity: number;
  price: number;

  commentText: string;
  idUser: number;
  idComponent: number;

  newComponent: MyComponent;
  activeComponent: MyComponent;
  mode: string;

  pages: number[];
  currentPage: number;

  typeName: string;
  required: boolean;
  cardinality: string;
  strings: string[];
  role: string;
  userId: any;

  manufacturerName;

  constructor(private router: Router,
    private permissionsService: NgxPermissionsService, private manufacturerService: ManufacturerService,
    private componentService: ComponentService, private toastr: ToastrService,  private translate: TranslateService) { }

  ngOnInit() {
    this.componentService.getAllComponents(0).then(components => {
      this.components = components['content'];
      this.pages = Array(components['totalPages']).fill(0).map((x, i) => i);
      this.currentPage = 0;
    });
    this.newManufacturerForm = new FormGroup({
      manufacturerName: new FormControl(),
    });
    this.componentService.getAllTypes().then(types => this.types = types);
    this.componentService.getAllManufacturers().then(manufacturers => this.manufacturers = manufacturers);


    this.initiateForms();
    this.mode = "";
    this.role = LoggedUtils.getRole();
    this.idUser = LoggedUtils.getId();

    const perm = [];
    perm.push(LoggedUtils.getRole());
    this.permissionsService.loadPermissions(perm);

    this.permissionsService.permissions$.subscribe((perm) => {
    })

  }

  add(addObj) {
    console.log(this.translate.currentLang);
    this.componentService.addNew(addObj).then(response => {
      this.changePageView(this.currentPage);
      this.initiateForms();
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Komponenta uspesno dodata!');        
      }
      else{
        this.toastr.success('Component successfully added!');        
      }
    }).catch(response => {
      this.toastr.error(response._body);
    });
  }

  update(addObj) {
    this.componentService.update(addObj).then(response => {
      this.changePageView(this.currentPage);
      this.initiateForms();
      
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Komponenta uspesno izmenjena!');        
      }
      else{
        this.toastr.success('Component successfully updated!');        
      }
    })
      .catch(response => {
        this.toastr.error(response._body);
      });
    ;
  }

  updateMode(comp) {
    this.activeComponent = comp;
    this.updateForm = new FormGroup({
      id: new FormControl(comp.id),
      name: new FormControl(comp.name, Validators.compose([Validators.required, Validators.minLength(5)])),
      type: new FormControl(comp.type.name, Validators.compose([Validators.required])),
      manufacturer: new FormControl(comp.manufacturer.name, Validators.compose([Validators.required])),
      quantity: new FormControl(comp.quantity, Validators.compose([Validators.required, Validators.pattern('[0-9]{1,5}')])),
      price: new FormControl(comp.price, Validators.compose([Validators.required, Validators.pattern('[0-9]{1,10}\.?[0-9]{0,2}')]))
    });
    this.mode = "update";
  }

  delete(deleteObj) {
    this.componentService.delete(deleteObj.id).then(response => {
      this.changePageView(this.currentPage);
      this.initiateForms();
      if(deleteObj.activeStatus==1){
        if(this.translate.currentLang=="srb"){
          this.toastr.success('Komponenta uspesno obrisana!');        
        }
        else{
          this.toastr.success('Component successfully deleted!');        
        }    
      }
      else{
        if(this.translate.currentLang=="srb"){
          this.toastr.success('Komponenta uspesno aktivirana!');        
        }
        else{
          this.toastr.success('Component successfully activated!');        
        } 
      }
      this.mode = "";
    })
      .catch(response => {
        this.toastr.error(response._body);
      });

  }

  toggleComment(comp) {
    this.activeComponent = comp;
    this.componentService.getAllCommentsById(comp.id).then(comments => {
      this.comments = comments;
      this.mode = "comments";
      this.addCommentForm = new FormGroup({
        commentText: new FormControl("", Validators.compose([Validators.required, Validators.minLength(5)])),
        idUser: new FormControl(4, Validators.compose([Validators.required])),
        idComponent: new FormControl(comp.id, Validators.compose([Validators.required]))
      });
    });

  }

  addComment(commentObj) {
    commentObj.idUser = LoggedUtils.getId();
    commentObj.idComponent = this.activeComponent.id;
    this.componentService.addComment(commentObj).then(response => {
      this.refreshComments();
      this.initiateForms();
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Komentar uspesno dodat!');        
      }
      else{
        this.toastr.success('Comment successfully added!');        
      }
      this.mode = "comments";
    })
      .catch(response => {
        this.toastr.error(response._body);
      });
  }

  deleteComment(id, compId) {
    this.componentService.deleteComment(id, compId).then(response => {
      this.refreshComments();
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Komentar uspesno obrisan!');        
      }
      else{
        this.toastr.success('Comment successfully deleted!');        
      }
      this.mode = "comments";
    })
      .catch(response => {
        this.toastr.error(response._body);
      });
  }

  newManufacturer(manufacturer){
    let name = Object.values(manufacturer)[0];
    this.manufacturerService.addNewManufacturer(manufacturer).then(result =>
      {
        this.manufacturers.push(result);
        if(this.translate.currentLang=="srb"){
          this.toastr.success('Proizvodjac uspesno dodat!');        
        }
        else{
          this.toastr.success('Manufacturer successfully added!');        
        }       
      })
      .catch(response => {
        this.toastr.error(response._body);
      });
  }

  newType(typeObj) {
    typeObj.required = true;
    this.strings = typeObj.cardinality.split('-');
    if (+this.strings[0] == 0) {
      typeObj.required = false;
    }
    else {
      typeObj.required = true;
    }
    typeObj.cardinality = this.strings[1];

    this.componentService.addType(typeObj).then(response => {
      response => this.types = response;
      this.refreshTypes();
      this.initiateForms();
      this.newTypeForm.reset();
      if(this.translate.currentLang=="srb"){
        this.toastr.success('Tip komponente uspesno dodat!');        
      }
      else{
        this.toastr.success('Component type successfully added!');        
      }
      this.manufacturerName = "";
    }).catch(response => {
      this.toastr.error(response._body);
    });
  }

  search(searchObj, page) {
    if (this.fromValue && this.toValue) {
      this.paramPrice = this.fromValue + "-" + this.toValue;
    }
    else {
      this.paramPrice = "";
    }

    if (this.paramPrice && this.checkboxValue && this.typeValue) {
      this.componentService.searchAll(this.paramPrice, "on", this.typeValue, page).then(components => {
        this.pageChanged(components);
      });
    }
    else if (this.paramPrice && this.typeValue) {
      this.componentService.searchByPriceAndType(this.paramPrice, this.typeValue, page).then(components => {
        this.pageChanged(components);
      });
    }

    else if (this.paramPrice && this.checkboxValue) {
      this.componentService.searchByPriceAndExist(this.paramPrice, "on", page).then(components => {
        this.pageChanged(components);
      });
    }

    else if (this.checkboxValue && this.typeValue) {
      this.componentService.searchByExistAndType("on", this.typeValue, page).then(components => {
        this.pageChanged(components);
      });
    }
    else if (this.paramPrice) {
      this.componentService.searchByPrice(this.paramPrice, page).then(components => {
        this.pageChanged(components);
      });
    }
    else if (this.checkboxValue) {
      this.componentService.searchByExist("on", page).then(components => {
        this.pageChanged(components);
      });
    }
    else if (this.typeValue) {
      this.componentService.searchByType(this.typeValue, page).then(components => {
        this.pageChanged(components);
      });
    }
    else {
      this.reset();
    }
  }

  refreshComments() {
    this.componentService.getAllCommentsById(this.activeComponent.id).then(comments => this.comments = comments);
  }

  refreshTypes() {
    this.componentService.getAllTypes().then(types => this.types = types);
  }

  pageChanged(components) {
    this.components = components['content'];
    this.pages = Array(components['totalPages']).fill(0).map((x, i) => i);
    this.currentPage = components['number'];
  }

  changePageView(numOfPage) {
    this.componentService.getAllComponents(numOfPage).then(components => {
      this.components = components['content'];
      this.pages = Array(components['totalPages']).fill(0).map((x, i) => i);
      this.currentPage = components['number'];
    });
  }

  changePageSearch(searchObj, numOfPage) {
    this.search(searchObj, numOfPage);
  }

  reset() {
    this.checkboxValue = false;
    this.typeValue = "";
    this.fromValue = "";
    this.toValue = "";
    this.paramPrice = "";
    this.mode = "";
    this.changePageView(0);
  }

  changeMode(newMode) {
    this.initiateForms();
    this.mode = newMode;
  }

  initiateForms() {
    this.searchForm = new FormGroup({

      toValue: new FormControl(),
      fromValue: new FormControl(),
      checkboxValue: new FormControl(),
      typeValue: new FormControl()

    });

    this.addForm = new FormGroup({
      name: new FormControl("", Validators.compose([Validators.required, Validators.minLength(5)])),
      type: new FormControl("", Validators.compose([Validators.required])),
      manufacturer: new FormControl("", Validators.compose([Validators.required])),
      quantity: new FormControl("", Validators.compose([Validators.required, Validators.pattern('[0-9]{1,5}')])),
      price: new FormControl("", Validators.compose([Validators.required, Validators.pattern('[0-9]{1,10}\.?[0-9]{0,2}')]))
    });

    this.updateForm = new FormGroup({
      id: new FormControl(""),
      name: new FormControl("", Validators.compose([Validators.required, Validators.minLength(5)])),
      type: new FormControl("", Validators.compose([Validators.required])),
      manufacturer: new FormControl("", Validators.compose([Validators.required])),
      quantity: new FormControl("", Validators.compose([Validators.required, Validators.pattern('[0-9]{1,5}')])),
      price: new FormControl("", Validators.compose([Validators.required, Validators.pattern('[0-9]{1,10}\.?[0-9]{0,2}')]))
    });


    this.addCommentForm = new FormGroup({
      commentText: new FormControl("", Validators.compose([Validators.required, Validators.minLength(5)])),
      idUser: new FormControl(""),
      idComponent: new FormControl(""),
    });

    this.newTypeForm = new FormGroup({
      name: new FormControl("", Validators.compose([Validators.required, Validators.minLength(5)])),
      required: new FormControl(),
      cardinality: new FormControl("")
    });
    this.addForm.reset();
    this.updateForm.reset();
    this.newTypeForm.reset();
    this.name = "";
    this.quantity = 0;
    this.price = 0;
  }

 
}