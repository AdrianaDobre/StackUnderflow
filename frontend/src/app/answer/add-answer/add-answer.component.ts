import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { AnswerService } from '../../service/answer.service';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-answer',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule, CommonModule, FormsModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule],
  templateUrl: './add-answer.component.html',
  styleUrl: './add-answer.component.scss'
})
export class AddAnswerComponent {
  answer = new FormControl('', [Validators.required])
  postId!:number;

  constructor(private answerService: AnswerService, private route: ActivatedRoute, private _snackBar:MatSnackBar) { }

  saveAnswer() {
    if (this.answer.valid){
      const comment = {
        postId: this.route.snapshot.paramMap.get('id'),
        body: this.answer.value
      }

      this.answerService.saveAnswer(comment).subscribe({
        next: (r) => {
          this._snackBar.open(r.message, "Dismiss", {
            duration:2000
          })
          setTimeout(function() {
            window.location.reload()
        }, 2000);
          
        },
        error: (e) => {
          this._snackBar.open(e.error.message, "Dismiss", {
            duration:2000
          })
        }
      })
    }
  }
}
