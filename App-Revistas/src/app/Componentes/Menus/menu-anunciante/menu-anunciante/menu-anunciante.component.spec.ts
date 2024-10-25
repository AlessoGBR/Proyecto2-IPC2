import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuAnuncianteComponent } from './menu-anunciante.component';

describe('MenuAnuncianteComponent', () => {
  let component: MenuAnuncianteComponent;
  let fixture: ComponentFixture<MenuAnuncianteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuAnuncianteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuAnuncianteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
