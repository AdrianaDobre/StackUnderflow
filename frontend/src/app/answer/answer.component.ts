import { Component, Input, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { AnswerService } from '../service/answer.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-answer',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule, CommonModule],
  templateUrl: './answer.component.html',
  styleUrl: './answer.component.scss'
})

export class AnswerComponent implements OnInit {
  @Input() 
  answer!:any;
  isLoggedIn!:boolean;

  constructor(private answerService:AnswerService, private authService:AuthService, private _snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
  }
  
  proceedUpvote(){
    this.answerService.upvoteAnswer(this.answer.answerId).subscribe({
      next: (r) => {
        //ok
      },
        error: (e) => {
          this._snackBar.open(e.error.message, "Dismiss", {
            duration:2000
          })
        }
      });
  }

  proceedDownvote(){
    this.answerService.downvoteAnswer(this.answer.answerId).subscribe({
      next: (r) => {
        //ok
      },
        error: (e) => {
          this._snackBar.open(e.error.message, "Dismiss", {
            duration:2000
          })
        }
      });;
  }

  deleteVote(){
    this.answerService.deleteVote(this.answer.answerId).subscribe(r=>r);
  }
}
