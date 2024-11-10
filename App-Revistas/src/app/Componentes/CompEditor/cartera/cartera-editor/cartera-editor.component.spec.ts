import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarteraEditorComponent } from './cartera-editor.component';

describe('CarteraEditorComponent', () => {
  let component: CarteraEditorComponent;
  let fixture: ComponentFixture<CarteraEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarteraEditorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarteraEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
