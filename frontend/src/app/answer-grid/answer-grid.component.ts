import { Component, Input, OnInit } from '@angular/core';
import { AnswerService } from '../service/answer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AnswerComponent } from '../answer/answer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-answer-grid',
  standalone: true,
  imports: [AnswerComponent, CommonModule],
  templateUrl: './answer-grid.component.html',
  styleUrl: './answer-grid.component.scss'
})
export class AnswerGridComponent implements OnInit {
  @Input() userId = '';
  answers?:Array<any>;
  
  constructor(private answerService:AnswerService, private route:ActivatedRoute, private router:Router){}
  
  ngOnInit(): void {
    this.answerService.retrieveAnswersByPostId(this.route.snapshot.paramMap.get("id")).subscribe(r => this.answers=r)  //bad but works
  }
}
