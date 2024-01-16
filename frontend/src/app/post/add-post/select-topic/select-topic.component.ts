import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { TopicService } from '../../../service/topic.service';
import { MatChipsModule } from '@angular/material/chips'; 
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-select-topic',
  standalone: true,
  imports: [MatChipsModule, CommonModule],
  templateUrl: './select-topic.component.html',
  styleUrl: './select-topic.component.scss'
})
export class SelectTopicComponent implements OnInit{
  @Output() onChipSelected = new EventEmitter();

  options?:Array<any>;
  selectedChips: any[] = [];


  constructor(private topicService:TopicService){ }

  ngOnInit(): void {
    this.topicService.getAllTopics().subscribe((r) => {
      this.options=r;
    });
  }

  changeSelected(parameter: string, query: any) {
    const index = this.selectedChips.indexOf(query);
    if (index >= 0) {
      this.selectedChips.splice(index, 1);
    } else {
      this.selectedChips.push({
        //sanitize object
        id:query.id,
        name:query.name
      });
    }
    
    this.onChipSelected.emit(this.selectedChips);
  }
}
