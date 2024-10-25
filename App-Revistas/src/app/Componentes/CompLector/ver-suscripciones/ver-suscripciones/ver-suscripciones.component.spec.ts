import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerSuscripcionesComponent } from './ver-suscripciones.component';

describe('VerSuscripcionesComponent', () => {
  let component: VerSuscripcionesComponent;
  let fixture: ComponentFixture<VerSuscripcionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerSuscripcionesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerSuscripcionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
