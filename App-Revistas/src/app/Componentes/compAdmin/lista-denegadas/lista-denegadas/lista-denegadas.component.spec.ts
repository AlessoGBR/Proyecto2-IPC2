import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaDenegadasComponent } from './lista-denegadas.component';

describe('ListaDenegadasComponent', () => {
  let component: ListaDenegadasComponent;
  let fixture: ComponentFixture<ListaDenegadasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaDenegadasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaDenegadasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
