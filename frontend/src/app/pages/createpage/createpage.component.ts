import { Component, OnInit, ViewChild } from "@angular/core";
import { MemeService } from "../../services/meme/meme.service";
import { Meme } from "../../models/Meme";
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthService } from "../../services/auth/auth.service";
import { TagService } from "app/services/tag/tag.service";
import { Tag } from "app/models/Tag";
import { CategoryService } from "app/services/category/category.service";
import { Category } from "app/models/Category";
import { HttpResponse } from "@angular/common/http";
import { NotifierService } from "angular-notifier";

let self: any;

@Component({
  selector: "ngx-createpage",
  templateUrl: "./createpage.component.html",
  styleUrls: ["./createpage.component.scss"],
})
export class CreatepageComponent implements OnInit {

  tagOptions: string[];
  tagIds: number[];
  categoryOptions: string[];
  categoryIds: number[];
  tagFilteredOptions$: Observable<string[]>;
  categoryFilteredOptions$: Observable<string[]>;
  addedCategoryId: number;
  addedTags: Tag[];
  allowedToMakeMemes: boolean;
  allowedToAddDescription: boolean;
  allowedToAddOwnImage: boolean;
  title: string;
  description: string;
  notifier: NotifierService;
  points: number;

  @ViewChild('tagInput') tagInput;
  @ViewChild('categoryInput') categoryInput;

  constructor(
    private memeService: MemeService,
    private authService: AuthService,
    private tagService: TagService,
    private categoryService: CategoryService,
    notifier: NotifierService,
  ) {
    this.notifier = notifier;
  }

  ngOnInit() {
    self = this;
    self.addedCategoryId = 0;
    self.addedTags = [];
    self.tagIds = [];
    self.categoryIds = [];
    this.userAllowedToMakeMeme();
    this.userAllowedToAddDescription();
    this.userAllowedToAddOwnImage();
    this.fillCanvas();
    this.tagService.getTags().pipe(
      tap((result)=>console.log(result))
    ).subscribe((tags: HttpResponse<Tag[]>)=>{
        this.tagOptions = [];
        tags.body.forEach(tag => {
          this.tagOptions.push(tag.title);
          this.tagIds.push(tag.id);
        })
        this.tagFilteredOptions$ = of(this.tagOptions);
        this.activateTagButton({title: "test"}, this.tagService, tags.body);
      }
      
    );
    this.categoryService.getCategories().pipe(
      tap((result)=>console.log(result))
    ).subscribe((categories: Category[])=>{
      this.categoryOptions = [];
      categories.forEach(category => {
        this.categoryOptions.push(category.title);
        this.categoryIds.push(category.id);
      })
      this.categoryFilteredOptions$ = of(this.categoryOptions);
      this.activateCategoryButton(this.categoryOptions);
    });
  }

  userAllowedToMakeMeme(){
    const user = this.authService.getCurrentUser();

    this.memeService.GetUserIsAllowedToCreateMeme(user.id).subscribe(result => {
      this.allowedToMakeMemes = result;
    });
  }

  userAllowedToAddDescription(){
    const user = this.authService.getCurrentUser();

    this.points = user.points;
    this.allowedToAddDescription = user.points >= 500;
  }

  userAllowedToAddOwnImage(){
    const user = this.authService.getCurrentUser();

    this.points = user.points;
    this.allowedToAddOwnImage = user.points >= 100;
  }

  activateTagButton(tag: Tag, tagService: TagService, tags: Tag[]){
    const tagbutton = document.querySelector('.add-tag-button');
        tagbutton.addEventListener("click", function(event){
          let tagInput = <HTMLInputElement>document.getElementById('tag');
          tag.title = tagInput.value;
          let add = true;
          let tagId = null;
          let addedTagsNotification = "";
          tags.forEach(oldTag => {
            if(tag.title == oldTag.title){
              add = false;
              tagId = oldTag.id;
            }
          })
          if(add){
            tagService.createTag(tag).subscribe((data: any)=> {
              console.log(data);
              tagId = data.body.id;
              self.tagOptions.push(data.body.title);
              tags.push(data.body);
              let addedTag = {id: tagId, title: tagInput.value};
              let condition = true;
              self.addedTags.forEach(oldAddedTag => {
                if(oldAddedTag.title == tag.title){
                  condition = false;
                }
                if(addedTagsNotification == ""){
                  addedTagsNotification = oldAddedTag.title;
                }
                else{
                  addedTagsNotification = addedTagsNotification + ", " + oldAddedTag.title;
                }
              });
              if(condition){
                self.addedTags.push(addedTag);
                if(addedTagsNotification == ""){
                  addedTagsNotification = tagInput.value;
                }
                else{
                  addedTagsNotification = addedTagsNotification + ", " + tagInput.value;
                }
              }
            });
          }
          else{
            let addedTag = {id: tagId, title: tagInput.value};
            let condition = true;
            self.addedTags.forEach(oldAddedTag => {
              if(oldAddedTag.title == tag.title){
                condition = false;
              }
              if(addedTagsNotification == ""){
                addedTagsNotification = oldAddedTag.title;
              }
              else{
                addedTagsNotification = addedTagsNotification + ", " + oldAddedTag.title;
              }
            });
            if(condition){
              self.addedTags.push(addedTag);
              if(addedTagsNotification == ""){
                addedTagsNotification = tagInput.value;
              }
              else{
                addedTagsNotification = addedTagsNotification + ", " + tagInput.value;
              }
            }
          }
          self.notifier.show({type: 'info', message: `The tag ${tagInput.value} has been added!\nYour tags are now: ${addedTagsNotification}`});
        }, false)
  }

  activateCategoryButton(categoryOptions: string[]){
    const categorybutton = document.querySelector('.add-category-button');
        categorybutton.addEventListener("click", function(event){
          let categoryInput = <HTMLInputElement>document.getElementById('category');
          let number = 0;
          categoryOptions.forEach(option => {
            if(option == categoryInput.innerText){
              self.addedCategoryId = self.categoryIds[number];
            }
            number = number + 1;
          });
          self.notifier.show({type: 'info', message: `The category ${categoryInput.innerText} has been added!\nYour category is now: ${categoryInput.innerText}`});
        }, false)
  }

  private tagFilter(value: string): string[] {
    const tagFilterValue = value.toLowerCase();
    return this.tagOptions.filter(tagOptionValue => tagOptionValue.toLowerCase().includes(tagFilterValue));
  }

  getTagFilteredOptions(value: string): Observable<string[]> {
    return of(value).pipe(
      map(filterString => this.tagFilter(filterString)),
    );
  }

  tagOnChange() {
    this.tagFilteredOptions$ = this.getTagFilteredOptions(this.tagInput.nativeElement.value);
  }

  tagOnSelectionChange($event){
    this.tagFilteredOptions$ = this.getTagFilteredOptions($event);
  }

  handleSaveImage(e) {
    var canvas = document.querySelector("canvas");
    console.log(canvas);
    var ctx = canvas.getContext("2d");
    var reader = new FileReader();
    reader.onload = function (event) {
      var img = new Image();
      img.onload = function () {
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0);
      };
      img.src = event.target.result as string;
      console.log("img src:  ");
      var temp = event.target.result as string;
      var image = document.querySelector("canvas");
      canvas.setAttribute("src", img.src);
    };

    reader.readAsDataURL(e.target.files[0]);
  }

  handleSaveImageFromTemplate(e) {
    var canvas = document.querySelector("canvas");
    console.log(canvas);
    var ctx = canvas.getContext("2d");
    var reader = new FileReader();
    reader.onload = function (event) {
      var img = new Image();
      img.onload = function () {
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0);
      };
      img.src = event.target.result as string;
      console.log("img src:  ");
      var temp = event.target.result as string;
      var image = document.querySelector("canvas");
      canvas.setAttribute("src", img.src);
    };

    fetch(e.target.src).then(r => r.blob()).then(blobFile => reader.readAsDataURL(blobFile));
  }

  fillCanvas() {
    var inputField = <HTMLInputElement>document.getElementById("inp");
    var canvas = document.querySelector("canvas");
    var ctx = canvas.getContext("2d");
    inputField.addEventListener("keyup", function () {
      var image = document.querySelector("canvas");
      var temp = canvas.getAttribute("src");
      canvas.width = image.width;
      canvas.setAttribute("crossOrigin", "Anonymous");
      canvas.height = image.height;
      ctx.drawImage(image, 0, 0);
      ctx.font = "64px Verdana";
      //redraw image
      ctx.clearRect(0, 0, image.width, image.height);

      var tempImg = new Image();
      tempImg.src = temp;
      ctx.drawImage(tempImg, 0, 0);
      ctx.fillStyle = "black";
      ctx.fillText(inputField.value, 40, 80);
    });

    var imageLoader = document.getElementById("imageLoader");
    console.log(imageLoader);
    if(self.allowedToAddOwnImage){
      imageLoader.addEventListener("change", this.handleSaveImage, false);
    }

    var savebutton = document.querySelector(".save-button");
    savebutton.addEventListener("click", this.saveMeme);
  }

  saveMeme() {
    var canvas = document.querySelector("canvas");

    const userId = self.authService.getCurrentUser().id;

    canvas.toBlob(function (blob) {
      var newImg = document.createElement("img"),
        url = URL.createObjectURL(blob);

        var meme: Meme = {
          title: self.title,
          description: self.description,
          categoryId: self.addedCategoryId,
          userId: userId,
          imageblob: blob,
          tags: self.addedTags
        };

        self.memeService.CreateMeme(meme).subscribe((res: HttpResponse<any>) => {
          console.log(res.body);
        });

      newImg.onload = function () {
        // no longer need to read the blob so it's revoked
        URL.revokeObjectURL(url);

        newImg.src = url;
        document.body.appendChild(newImg);
      };
    });
  }
}
