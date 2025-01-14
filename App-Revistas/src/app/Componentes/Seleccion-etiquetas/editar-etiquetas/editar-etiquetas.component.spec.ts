import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarEtiquetasComponent } from './editar-etiquetas.component';

describe('EditarEtiquetasComponent', () => {
  let component: EditarEtiquetasComponent;
  let fixture: ComponentFixture<EditarEtiquetasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarEtiquetasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarEtiquetasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
