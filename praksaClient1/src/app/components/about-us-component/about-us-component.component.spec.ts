import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutUsComponentComponent } from './about-us-component.component';

describe('AboutUsComponentComponent', () => {
  let component: AboutUsComponentComponent;
  let fixture: ComponentFixture<AboutUsComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AboutUsComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AboutUsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
