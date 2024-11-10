import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnunciosAprobadosComponent } from './anuncios-aprobados.component';

describe('AnunciosAprobadosComponent', () => {
  let component: AnunciosAprobadosComponent;
  let fixture: ComponentFixture<AnunciosAprobadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnunciosAprobadosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnunciosAprobadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
