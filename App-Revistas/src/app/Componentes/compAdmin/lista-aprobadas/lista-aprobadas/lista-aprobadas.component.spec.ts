import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaAprobadasComponent } from './lista-aprobadas.component';

describe('ListaAprobadasComponent', () => {
  let component: ListaAprobadasComponent;
  let fixture: ComponentFixture<ListaAprobadasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaAprobadasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaAprobadasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
