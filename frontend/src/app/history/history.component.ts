import { Component } from '@angular/core';
import { AnswerService } from '../service/answer.service';
import { ActivatedRoute } from '@angular/router';
import { AnswerComponent } from '../answer/answer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [AnswerComponent, CommonModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.scss'
})
export class HistoryComponent {
  answer!:any;
  history!:any;

  constructor(private answerService:AnswerService, private route:ActivatedRoute){}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.answerService.getAnswerById(id).subscribe(r=>{this.answer=r;});
    this.answerService.getHistoryForAnswer(id).subscribe(r=>{this.history=r});
  }
}
