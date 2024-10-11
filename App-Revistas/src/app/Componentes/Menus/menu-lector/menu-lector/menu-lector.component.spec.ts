import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuLectorComponent } from './menu-lector.component';

describe('MenuLectorComponent', () => {
  let component: MenuLectorComponent;
  let fixture: ComponentFixture<MenuLectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuLectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuLectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
