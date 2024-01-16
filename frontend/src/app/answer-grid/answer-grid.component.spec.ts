import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerGridComponent } from './answer-grid.component';

describe('AnswerGridComponent', () => {
  let component: AnswerGridComponent;
  let fixture: ComponentFixture<AnswerGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnswerGridComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnswerGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
