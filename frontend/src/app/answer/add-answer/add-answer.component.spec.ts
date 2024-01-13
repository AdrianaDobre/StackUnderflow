import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAnswerComponent } from './add-answer.component';

describe('AddAnswerComponent', () => {
  let component: AddAnswerComponent;
  let fixture: ComponentFixture<AddAnswerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddAnswerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddAnswerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
