import { Component, Input, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { AnswerService } from '../service/answer.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../service/auth.service';
import { PostService } from '../service/post.service';
import { ActivatedRoute, Router } from '@angular/router';

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
  @Input()
  isSuggestion:boolean=false;
  @Input()
  isHistory:boolean=false;
  isLoggedIn!:boolean;
  userId!:string;

  @Input() postUserId = '';

  constructor(private answerService:AnswerService, private authService:AuthService, private _snackBar:MatSnackBar, private router:Router, private postService:PostService, private route:ActivatedRoute) { }

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

  goToAnswer(id: string) {
    this.router.navigateByUrl('/answer/' + id + '/suggest');
  }

  getUserIdFromToken() { 
    return this.authService.getUserIdFromToken()
  }

  isLoggedInFunc() {
    return this.authService.isLoggedIn()
  }

  // getPostUserId(){
  //   return this.postService.getPostById(this.postId).userId
  // }

  //
  acceptSuggestion(){
    if(this.isSuggestion){
      const id = this.route.snapshot.paramMap.get('id');
      this.answerService.acceptSuggestion(id, this.answer.suggestionId).subscribe({
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
      });
    }
  }

  goToHistory(id: string){
    this.router.navigateByUrl('/answer/' + id + '/history');
  }

}
