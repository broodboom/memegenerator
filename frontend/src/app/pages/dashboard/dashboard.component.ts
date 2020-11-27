import {Component, OnDestroy} from '@angular/core';

//TODO: move this to a generic model folder
class Card{
  title: string;
  image: string;
  upvotes: number;
  downvotes: number;

  constructor(title: string, image: string){
    this.title = title;
    this.image = image;
    this.upvotes = 0;
    this.downvotes = 0;
  }
}

function getPlaceholderCards(){
  let items = new Array();
  var item1 = new Card("meme1", "https://placekitten.com/200/300");
  var item2 = new Card("meme2", "https://placekitten.com/200/300");
  var item3 = new Card("meme3", "https://placekitten.com/200/300");
  var item4 = new Card("meme4", "https://placekitten.com/200/300");
  items.push(item1);
  items.push(item2);
  items.push(item3);
  items.push(item4);

  return items;
}


@Component({
  selector: 'ngx-dashboard',
  styleUrls: ['./dashboard.component.scss'],
  templateUrl: './dashboard.component.html',
})


export class DashboardComponent {
  
  items = getPlaceholderCards();
}
