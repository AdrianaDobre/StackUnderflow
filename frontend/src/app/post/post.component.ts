import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { PostService } from '../service/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AnswerGridComponent } from '../answer-grid/answer-grid.component';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule, CommonModule, AnswerGridComponent],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss'
})
export class PostComponent implements OnInit{
  postId!:string;
  title!:string;
  body!:string;
  tags?:Array<string>;
  bestAnswer?:string;
  votesByLoggedUser?:Array<any>;
  createdDate!:Date;
  userName!:string;

  constructor(private postService:PostService, private route:ActivatedRoute, private router:Router){ }

  ngOnInit(): void {
    this.postId=this.route.snapshot.paramMap.get("id")!;
    this.postService.retrievePostById(this.postId).subscribe({
      next: (r) => {
        this.title=r.title,
        this.body=r.body,
        this.tags=r.tags,
        this.bestAnswer=r.bestAnswer,
        this.votesByLoggedUser=r.votesByLoggedUser;
        this.createdDate=r.createdDate;
        this.userName=r.userName;
      },
        error: (e) => {
          console.log(e)
            this.router.navigateByUrl('/404')
          }
      })
  }
}
