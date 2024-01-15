import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { SuggestionService } from '../../service/suggestion.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-suggestion',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule, CommonModule, FormsModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule],
  templateUrl: './add-suggestion.component.html',
  styleUrl: './add-suggestion.component.scss'
})
export class AddSuggestionComponent implements OnInit {
  @Input()
  answerText!:string;
  @Input()
  answerId!:string;
  answer = new FormControl('', [Validators.required])

  constructor(private suggestionService:SuggestionService, private _snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.answer.setValue(this.answerText);
  }

  proceedPostSuggestion(){
    const suggestion = {
      answerId:this.answerId,
      body:this.answer.value,
    }
    this.suggestionService.postSuggestion(suggestion).subscribe({
      next: (r) => {
        this._snackBar.open(r.message, "Dismiss", {
          duration:2000
        })      
      },
      error: (e) => {
        this._snackBar.open(e.error.message, "Dismiss", {
          duration:2000
        })
      }
    })
  }
}
