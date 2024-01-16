import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectTopicComponent } from './select-topic.component';

describe('SelectTopicComponent', () => {
  let component: SelectTopicComponent;
  let fixture: ComponentFixture<SelectTopicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectTopicComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SelectTopicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
