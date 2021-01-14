//#define __STDC_WANT_LIB_EXT1__ 1 //Required for string operations
#include<iostream>//Import Statements
#include<fstream>//Import Statements
#include<cctype>//Import Statements
#include<vector>//Import Statements
#include<cstring>//Import Statements
#include<algorithm>//Import Statements
#include <string.h>//Import Statements

using namespace std;//Using standard input output stream
struct node {
    char Data;//Store alphabet
    bool wEnd;//boolean var true of word end
    vector<node*> wChildren;//store pointers of node
     //Initialize struct node using constructor
    node() {
        Data = ' ';
        wEnd = false;
    }
};

node* root;//Global Variable

char Data(node* root) {
    return root->Data;//Return alphabet 
}

node setData(char c, node* root) {
    root->Data = c;//sets char at root->data 
    return *root;
}

bool WordTerminate(node* root)
{
    return root->wEnd;//return  boolean value
}

node setWordTerminate(node* root)
{
    root->wEnd = true;//sets boolean value true
    return *root;
}

void appendChild(node* tmp, node* current)
{
    current->wChildren.push_back(tmp);//Insert node pointer into vector

}

vector<node*> children(node* root)
{
    return root->wChildren;//return vector
}

node* findingChild(char c, node* root)
{
    for (int i = 0; i < root->wChildren.size(); i++)
    {
        node* temp1 = root->wChildren.at(i);//returns the character at the specified position
        if (Data(temp1) == c)//checks for alphabet if true then it's node pointer
        {
            return temp1;
        }
    }
    return NULL;
}

void addWord(string s, node* root)
{
    node* current = root;
    if (s.length() == 0)
    {
        setWordTerminate(current); // an empty word
        return;
    }
    //Not an empty word
    for (int i = 0; i < s.length(); i++)
    {
        node* child = findingChild(s[i], current);//return node pointer of particular aplhabet
        if (child != NULL)
        {
            current = child;
        }
        else
        {
            node* tmp = new node();//Create new node
            setData(s[i], tmp);//Set data in tmp node
            appendChild(tmp, current);//Push tmp pointer to currrent node's vector
            current = tmp;
        }
        if (i == s.length() - 1)
            setWordTerminate(current);//Set wEnd as true
    }
}

void TraverseTree(node* current, char* s, vector<string>& res, bool& loop)
{
    char k[100] = { 0 };//Stores each suggested word
    char a[2] = { 0 };
    if (loop)
    {
        if (current != NULL)
        {
            if (WordTerminate(current) == true)
            {
                res.push_back(s);//res is ListofWords
                if (res.size() > 20)//Set loop as false if suggested words are > 20
                    loop = false;
            }
            vector<node*> child = children(current);//Stores all children of that particular node in child vector
            for (int j = 0; j < child.size() && loop; j++)
            {
                //strcpy_s(k, s);//Copy s to k
                strcpy(k, s);
                a[0] = Data(child[j]);//returns char at given index position
                a[1] = '\0';
                //strcat_s(k, a);///k=a,k=ab/0
                strcat(k, a);
                if (loop)
                    TraverseTree(child[j], k, res, loop);//Recursively call TraverseTree function until loop is true
            }
        }
    }
}

bool Complete(string s, vector<string>& res, node* root)
{
    node* current = root;
    for (int i = 0; i < s.length(); i++)
    {
        node* tmp = findingChild(s[i], current);//return node pointer of particular aplhabet
        if (tmp == NULL) //Checks for nu;;          
            return false;
        current = tmp;
    }
    
    char c[100];
    //strcpy_s(c, s.c_str());//Copy content of partial word in c
    strcpy(c, s.c_str());
    bool loop = true;
    TraverseTree(current, c, res, loop);
    return true;
}


bool loadWordList(node* trie, string fn)
{
    ifstream words;//Declare object of ifstream class
    
    cout << "Entered in load WordList" << endl;
    words.open(fn.c_str());//Opening wordList file
    if (!words.is_open())
    {
        cout << "Could not open WordList file" << endl;
        return false;
    }
    //If file is open
    while (!words.eof())
    {

        char s[100];
        words >> s;//Copying each line of file to s until eof

        addWord(s, trie);
    }
    cout << "WordList loaded";
    words.close();//closing file
    
    return true;
}

void WriteNewWord(node* trie)
{
    cout << "Enter the word : ";
    string NewWord;
    cin >> NewWord;
    bool OnlyAlpha = true;
    for (int i = 0; i < NewWord.length(); i++)
    {
        if (!isalpha(NewWord[i]))//Check are the aplhabetical characters only or not
        {
            OnlyAlpha = false;
            break;
        }
    }
    if (OnlyAlpha)//Executes until OnlyApha is true
    {
        transform(NewWord.begin(), NewWord.end(), NewWord.begin(), ::tolower);//converts string to lower case
        vector<string> ListOfWords;//Vectoe to store suggestions
        Complete(NewWord, ListOfWords, trie);//Checks for new word is in trie or not
        if (ListOfWords.size() != 0)//if Entered word exists
        {
            cout << "The word '" << NewWord << "' already exists in the dictionary.\n";
            return;
        }
        else//Entered don't exists in trie
        {
            ofstream out;//Declare object of ofstream class
            out.open("WordList.txt", ios::app);//Opening wordList file
            if (!out.is_open())//if file is not open
            {
                cout << "Sorry!\nCould not open the dictionary!\n";
                out.close();
                return;
            }
            //if file is opened
            else
            {
                out << NewWord << "\n";//Entered word is written to wordfile
                cout << "Successfully loaded in the dictionary!\n";
                out.close();
                addWord(NewWord, trie);//Insert entered word to trie
                return;
            }
        }
    }
    else//If new word don't contains aplhabetical character
    {
        cout << "\nNot a valid word!\n";
        return;
    }
}

int main()
{
    root = new node(); //Where new is used to allocate memory for a C++ class object, the object's constructor is called after the memory is allocated
    char mode;
    cout << "Loading the WordList file" << endl;
    loadWordList(root, "WordList.txt");
    while (1)
    {
        cout << endl << endl;
        cout << "Interactive mode,press " << endl;
        cout << "1: Auto Complete" << endl;
        cout << "2: Enter new words into the word list\n";
        cout << "3: Quit" << endl << endl;
        cin >> mode;
        if (isalpha(mode))//Checks for 
        {
            cout << "Invalid Input!\n";
            cout << "Enter either 1 or 2..";
            continue;
        }
        switch (mode)
        {
        case '1':
        {
            string s;
            cout << "Enter the partial word : ";
            cin >> s;
            transform(s.begin(), s.end(), s.begin(), ::tolower);
            vector<string> ListOfWords;
            Complete(s, ListOfWords, root);
            if (ListOfWords.size() == 0)
            {
                cout << "Sorry!\nNo suggestions" << endl;
                cout << "Do you want to enter this word into the memory?(y/n) : ";
                char pp;
                cin >> pp;
                if (pp == 'y' || pp == 'Y')
                {
                    WriteNewWord(root);//If partial word isn't present in trie then write it to list
                }
            }
            else
            {
                cout << "Auto complete reply :" << endl;
                for (int i = 0; i < ListOfWords.size(); i++)
                {
                    cout << " \t     " << ListOfWords[i] << endl;
                }
            }
        }
        continue;

        case '2':
            WriteNewWord(root);
            continue;

        case '3':
            delete root;//Deallocates memory given to root
            return 0;
        default:
            cout << "Invalid input!";
            cout << "\nEnter either 1 or 2..";
            continue;
        }
    }
}
