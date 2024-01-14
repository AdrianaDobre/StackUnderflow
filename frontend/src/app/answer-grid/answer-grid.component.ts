import { Component, OnInit } from '@angular/core';
import { AnswerService } from '../service/answer.service';
import { ActivatedRoute } from '@angular/router';
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
  answers?:Array<any>;

  constructor(private answerService:AnswerService, private route:ActivatedRoute){}
  
  ngOnInit(): void {
    this.answerService.retrieveAnswersByPostId(this.route.snapshot.paramMap.get("id")).subscribe(r => this.answers=r)  //bad but works
  }
}
