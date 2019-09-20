import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CampoFechaComponent } from './campo-fecha.component';

describe('CampoFechaComponent', () => {
  let component: CampoFechaComponent;
  let fixture: ComponentFixture<CampoFechaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CampoFechaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CampoFechaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
