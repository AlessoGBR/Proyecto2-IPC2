import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaEsperaComponent } from './lista-espera.component';

describe('ListaEsperaComponent', () => {
  let component: ListaEsperaComponent;
  let fixture: ComponentFixture<ListaEsperaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaEsperaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaEsperaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
