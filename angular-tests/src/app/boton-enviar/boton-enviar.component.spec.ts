import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonEnviarComponent } from './boton-enviar.component';

describe('BotonEnviarComponent', () => {
  let component: BotonEnviarComponent;
  let fixture: ComponentFixture<BotonEnviarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BotonEnviarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BotonEnviarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
