import { Component, OnInit } from '@angular/core';
import { AnswerComponent } from '../answer/answer.component';
import { AnswerService } from '../service/answer.service';
import { ActivatedRoute } from '@angular/router';
import { AddSuggestionComponent } from './add-suggestion/add-suggestion.component';

@Component({
  selector: 'app-suggestion',
  standalone: true,
  imports: [AnswerComponent, AddSuggestionComponent],
  templateUrl: './suggestion.component.html',
  styleUrl: './suggestion.component.scss'
})
export class SuggestionComponent implements OnInit {
  answer!:any;

  constructor(private answerService:AnswerService, private route:ActivatedRoute){}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.answerService.getAnswerById(id).subscribe(r=>{this.answer=r;});
  }
}
