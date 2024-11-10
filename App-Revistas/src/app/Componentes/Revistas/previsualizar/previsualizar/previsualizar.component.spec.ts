import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrevisualizarComponent } from './previsualizar.component';

describe('PrevisualizarComponent', () => {
  let component: PrevisualizarComponent;
  let fixture: ComponentFixture<PrevisualizarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrevisualizarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrevisualizarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
