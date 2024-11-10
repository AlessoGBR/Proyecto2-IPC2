import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteEditorComponent } from './reporte-editor.component';

describe('ReporteEditorComponent', () => {
  let component: ReporteEditorComponent;
  let fixture: ComponentFixture<ReporteEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteEditorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
