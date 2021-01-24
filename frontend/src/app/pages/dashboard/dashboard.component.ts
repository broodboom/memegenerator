import {Component, OnInit} from '@angular/core';
import { Meme } from 'app/models/Meme';
import { MemeService } from 'app/services/meme/meme.service';
import { DomSanitizer } from '@angular/platform-browser';
import { WebSocketAPI } from './WebSocketAPI';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CategoryService } from "app/services/category/category.service";
import { Category } from "app/models/Category";
import { Observable, of } from 'rxjs';
import { FormGroup, FormControl, Validators} from '@angular/forms';

//TODO: move this to a generic model folder
class Card{
  id: number;
  title: string;
  image: string;
  upvotes: number;
  downvotes: number;

  constructor(title: string, image: string, id: number){
    this.title = title;
    this.image = image;
    this.id = id
    this.upvotes = 0;
    this.downvotes = 0;
  }
}

class ResponseClass {
  memeId: string;
  isUpvote: boolean;

  constructor(jsonStr: string) {
    let jsonObj: any = JSON.parse(jsonStr);
    for (let prop in jsonObj) {
        this[prop] = jsonObj[prop];
    }
  }
}

let self: any;

@Component({
  selector: 'ngx-dashboard',
  styleUrls: ['./dashboard.component.scss'],
  templateUrl: './dashboard.component.html',
})


export class DashboardComponent implements OnInit{
  searchValue: string
  categoryOptions: string[];
  categoryIds: number[];
  categoryFilteredOptions$: Observable<string[]>;
  addedCategoryId: number;
  webSocketAPI: WebSocketAPI;
  items = []
  searchItems = []
  loading = false;

  constructor(public memeService: MemeService, private categoryService: CategoryService,private sanitizer : DomSanitizer, private route: ActivatedRoute
    , private router: Router){
      this.searchValue = ""
    }

  loadNext(){
    if(this.loading) {return}
    this.loading = true;
    // this.memeService.GetAllMemes().subscribe(meme => this.items.push(...meme));
    this.loading = false;
  }

  searchMemes(){
    this.searchItems = this.items.filter((i) => i.title == this.searchValue);
  }
  
  ngOnInit(){
    self = this;
    self.categoryIds = [];
    this.webSocketAPI = new WebSocketAPI(this);
    this.connect();
    this.route.queryParamMap.forEach((params: Params) => {
      console.log("params: ",params.params.category);
      let category = params.params.category; 
      if(category != null){
        this.memeService.GetAllMemesFilteredOnCategory(category).subscribe(meme => this.inserItem(meme));
      }
      else{
        this.memeService.GetAllMemes().subscribe(meme => this.inserItem(meme));
      } 
      this.categoryService.getCategories().pipe(
      ).subscribe((categories: Category[])=>{
        this.categoryOptions = [];
        categories.forEach(category => {
          this.categoryOptions.push(category.title);
          this.categoryIds.push(category.id);
        })
        this.categoryFilteredOptions$ = of(this.categoryOptions);
        this.addFilters(this.categoryOptions);
        // this.activateCategoryButton(this.categoryOptions);
      });
      
  });
  }

  ngOnDestroy(){
    this.disconnect();
  }

  sendMessage(voteType, item){
    let response;
    if(voteType === "u"){
      response = {memeId: item.id,isUpvote: true}
    }else{
      response = {memeId: item.id,isUpvote: false}
    }

    this.webSocketAPI._send(response);
  }

  connect(){
    this.webSocketAPI._connect();
  }

  disconnect(){
    this.webSocketAPI._disconnect();
  }

  inserItem(meme: Meme[]){
    meme.forEach(function(element){
      element.imageSrc = 'data:image/png;base64,' + element.imageblob;

      self.items.push(element)
    })
  }

  handleMessage(message){
    let fObj: ResponseClass = new ResponseClass(message);
    var item2 = self.items.filter(function(item) {
      return item.id === fObj.memeId;
    })[0];

    if(fObj.isUpvote){
      item2.likes++;
    }else{
      item2.dislikes++;
    }
  }
  
  getFilters(e){
    console.log(e.target.value);
    self.items = [];
    this.memeService.GetAllMemesFilteredOnCategory(e.target.value).subscribe(meme => this.inserItem(meme));
  }

  addFilters(categoryOptions: string[]){
    let categoryInput = <HTMLInputElement>document.getElementById('category');
    let number = 0;
    categoryOptions.forEach(option => {
      if(option == categoryInput.innerText){
        self.addedCategoryId = self.categoryIds[number];
      }
      number = number + 1;
    });
  }
}
