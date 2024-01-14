import { Component } from '@angular/core';
import { PostComponent } from '../post/post.component';
import { PostService } from '../service/post.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [PostComponent, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  postIds?:Array<string>;

  constructor(private postService:PostService) { }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe({
      next: (r) => {
        this.postIds = r.map((post: { id: any; }) => post.id)
      }})
  }
}
